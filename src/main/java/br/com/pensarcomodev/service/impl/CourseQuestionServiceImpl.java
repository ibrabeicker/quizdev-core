package br.com.pensarcomodev.service.impl;

import br.com.pensarcomodev.dto.ChoiceQuestionSubmissionDto;
import br.com.pensarcomodev.entity.Choice;
import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.repository.QuestionRepository;
import br.com.pensarcomodev.service.CourseQuestionService;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class CourseQuestionServiceImpl implements CourseQuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public void submitChoiceQuestion(ChoiceQuestionSubmissionDto dto, Principal user) {
        Question question = questionRepository.findById(dto.getQuestionId()).orElseThrow();
        Map<Integer, Boolean> rightAnswers = question.getChoices().stream()
                .collect(Collectors.toMap(Choice::getId, Choice::isRight));
    }
}
