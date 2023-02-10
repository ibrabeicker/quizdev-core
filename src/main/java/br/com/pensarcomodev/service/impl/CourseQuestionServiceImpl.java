package br.com.pensarcomodev.service.impl;

import br.com.pensarcomodev.dto.ChoiceQuestionDto;
import br.com.pensarcomodev.dto.CourseQuestionViewDto;
import br.com.pensarcomodev.dto.ProgrammingQuestionSubmissionDto;
import br.com.pensarcomodev.dto.ProgrammingQuestionResponseDto;
import br.com.pensarcomodev.entity.Choice;
import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.entity.SubmittedAnswer;
import br.com.pensarcomodev.entity.SubmittedProgrammingAnswer;
import br.com.pensarcomodev.mapper.QuestionMapper;
import br.com.pensarcomodev.microservices.codeeval.dto.AnswerCodeExecutionDto;
import br.com.pensarcomodev.microservices.codeeval.dto.AnswerCodeExecutionResponseDto;
import br.com.pensarcomodev.repository.QuestionRepository;
import br.com.pensarcomodev.repository.SubmittedAnswerRepository;
import br.com.pensarcomodev.repository.SubmittedProgrammingAnswerRepository;
import br.com.pensarcomodev.service.CourseQuestionService;
import br.com.pensarcomodev.service.JavaSandboxIntegrationService;
import br.com.pensarcomodev.utils.HashId;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class CourseQuestionServiceImpl implements CourseQuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final SubmittedAnswerRepository submittedAnswerRepository;
    private final JavaSandboxIntegrationService javaSandboxIntegrationService;
    private final SubmittedProgrammingAnswerRepository submittedProgrammingAnswerRepository;

    @Override
    public CourseQuestionViewDto getQuestionView(String id) {
        Question question = questionRepository.findById(HashId.decodeHash(id)).orElseThrow();
        return questionMapper.toViewDto(question);
    }

    @Override
    public ChoiceQuestionDto submitChoiceQuestion(ChoiceQuestionDto dto, Principal user) {
        Long questionId = HashId.decodeHash(dto.getQuestionId());
        Question question = questionRepository.findById(questionId).orElseThrow();
        Map<Integer, Choice> choicesMap = question.getChoices().stream().collect(Collectors.toMap(Choice::getId, i -> i));
        for (ChoiceQuestionDto.Answer answer : dto.getAnswers()) {
            int answerId = answer.getId();
            Choice choice = choicesMap.get(answerId);
            boolean rightResponse = answer.getSelected().equals(choice.isValue());
            answer.setRight(rightResponse);
            answer.setExplanationSource(choice.getExplanationSourceCode());
        }
        SubmittedAnswer submittedAnswer = registerSubmittedQuestion(dto, question);
        dto.setAnswerId(HashId.hash(submittedAnswer.getId()));
        return dto;
    }

    @Override
    public ProgrammingQuestionResponseDto submitCodeExecutionQuestion(ProgrammingQuestionSubmissionDto dto) {
        Question question = questionRepository.findById(HashId.decodeHash(dto.getQuestionId())).orElseThrow();

        SubmittedProgrammingAnswer answerEntity = createAnswer(dto, question);
        AnswerCodeExecutionDto codeDto = from(answerEntity, question);

        Optional<AnswerCodeExecutionResponseDto> codeExecutionResponse = javaSandboxIntegrationService.submitCodeExecution(
                codeDto);
        if (codeExecutionResponse.isPresent()) {
            AnswerCodeExecutionResponseDto executionResponse = codeExecutionResponse.get();
            updateEntity(answerEntity, executionResponse);
            return fromResponse(executionResponse);
        } else {

        }
    }

    private void updateEntity(SubmittedProgrammingAnswer answerEntity, AnswerCodeExecutionResponseDto executionResponse) {
        answerEntity.setFullScore(executionResponse.isFullScore());
        answerEntity.setEvalStatus(executionResponse.getEvalStatus());
        answerEntity.setErrorMessage(executionResponse.getErrorMessage());
        submittedProgrammingAnswerRepository.save(answerEntity);
    }

    private ProgrammingQuestionResponseDto fromResponse(AnswerCodeExecutionResponseDto executionResponse) {
        ProgrammingQuestionResponseDto responseDto = new ProgrammingQuestionResponseDto();
        responseDto.setSuccess(executionResponse.isFullScore());
        List<String> failedTestCases = executionResponse.getTestCases().stream()
                .filter(i -> !i.isPassed())
                .map(i -> i.getTestCase().getCallCode())
                .collect(Collectors.toList());
        responseDto.setFailingTestCases(failedTestCases);
        return responseDto;
    }

    private SubmittedAnswer registerSubmittedQuestion(ChoiceQuestionDto dto, Question question) {
        SubmittedAnswer submittedAnswer = new SubmittedAnswer();
        submittedAnswer.setQuestion(question);
        submittedAnswer.setStudentId(dto.getStudentId());
        submittedAnswer.setFullScore(dto.getAnswers().stream().allMatch(ChoiceQuestionDto.Answer::getRight));
        submittedAnswer.setChoices(questionMapper.fromSubmittedAnswers(dto.getAnswers()));
        SubmittedAnswer saved = submittedAnswerRepository.save(submittedAnswer);
        log.info("{}", submittedAnswer);
        return saved;
    }

    private SubmittedProgrammingAnswer createAnswer(ProgrammingQuestionSubmissionDto dto, Question question) {
        SubmittedProgrammingAnswer answer = new SubmittedProgrammingAnswer();
        answer.setQuestion(question);
        answer.setSubmittedCode(dto.getSubmittedCode());
        answer.setStudentId(dto.getStudentId());
        return submittedProgrammingAnswerRepository.save(answer);
    }

    private AnswerCodeExecutionDto from(SubmittedProgrammingAnswer answer, Question question) {
        AnswerCodeExecutionDto codeDto = new AnswerCodeExecutionDto();
        codeDto.setQuestionId(HashId.hash(question.getId()));
        codeDto.setSubmittedCode(answer.getSubmittedCode());
        codeDto.setPreExecutionCode(question.getProgrammingQuestion().getPreExecutionCode());
        codeDto.setStudentId(answer.getStudentId());
        codeDto.setTestCases(question.getProgrammingQuestion().getTestCases());
        codeDto.setRequestId(answer.getId().toString());
        return codeDto;
    }
}
