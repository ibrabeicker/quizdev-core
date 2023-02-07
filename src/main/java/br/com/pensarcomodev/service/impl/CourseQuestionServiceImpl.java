package br.com.pensarcomodev.service.impl;

import br.com.pensarcomodev.dto.ChoiceQuestionDto;
import br.com.pensarcomodev.entity.Choice;
import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.entity.SubmittedAnswer;
import br.com.pensarcomodev.mapper.QuestionMapper;
import br.com.pensarcomodev.repository.QuestionRepository;
import br.com.pensarcomodev.repository.SubmittedAnswerRepository;
import br.com.pensarcomodev.service.CourseQuestionService;
import br.com.pensarcomodev.utils.HashId;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class CourseQuestionServiceImpl implements CourseQuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final SubmittedAnswerRepository submittedAnswerRepository;

//    @Override
    public ChoiceQuestionDto submitChoiceQuestion(ChoiceQuestionDto dto, Principal user) {
        dto.setStudentId(user.getName());
        Long questionId = HashId.decodeHash(dto.getQuestionId());
        Question question = questionRepository.findById(questionId).orElseThrow();
        Map<Integer, Choice> choicesMap = question.getChoices().stream().collect(Collectors.toMap(Choice::getId, i -> i));
        for (ChoiceQuestionDto.Answer answer : dto.getAnswers()) {
            int answerId = answer.getId();
            Choice choice = choicesMap.get(answerId);
            boolean rightResponse = answer.getSelected().equals(choice.isValue());
            answer.setRight(rightResponse);
            answer.setSourceType(choice.getSourceType());
            answer.setExplanationSource(choice.getExplanationSourceCode());
        }
        return dto;
    }

    private void registerSubmittedQuestion(ChoiceQuestionDto dto, Question question) {
        SubmittedAnswer submittedAnswer = new SubmittedAnswer();
        submittedAnswer.setQuestion(question);
        submittedAnswer.setStudentId(dto.getStudentId());
        submittedAnswer.setFullScore(dto.getAnswers().stream().allMatch(ChoiceQuestionDto.Answer::getRight));
        submittedAnswer.setChoices(questionMapper.fromSubmittedAnswers(dto.getAnswers()));
        submittedAnswerRepository.save(submittedAnswer);
        log.info("{}", submittedAnswer);
    }
}
