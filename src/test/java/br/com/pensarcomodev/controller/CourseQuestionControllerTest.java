package br.com.pensarcomodev.controller;

import br.com.pensarcomodev.dto.ChoiceQuestionDto;
import br.com.pensarcomodev.entity.Choice;
import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.entity.SubmittedAnswer;
import br.com.pensarcomodev.entity.enums.QuestionType;
import br.com.pensarcomodev.entity.enums.SourceType;
import br.com.pensarcomodev.repository.QuestionRepository;
import br.com.pensarcomodev.repository.SubmittedAnswerRepository;
import br.com.pensarcomodev.utils.HashId;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseQuestionControllerTest extends AbstractControllerTest {

    private static final String PATH = "/course-question";
    private static final String PATH_PREFIX = PATH + "/";

    private Question question;

    @Inject
    private QuestionRepository questionRepository;

    @Inject
    private SubmittedAnswerRepository submittedAnswerRepository;

    @BeforeEach
    public void setup() {
        questionRepository.deleteAll();
        setupQuestion();
    }

    @Test
    public void testSubmittAllRightQuestion() {

        ChoiceQuestionDto dto = new ChoiceQuestionDto();
        dto.setQuestionId(HashId.hash(question.getId()));
        dto.setStudentId("abc123");
        dto.setAnswers(new ArrayList<>());
        dto.getAnswers().add(buildAnswer(1, true));
        dto.getAnswers().add(buildAnswer(2, false));
        dto.getAnswers().add(buildAnswer(3, true));
        dto.getAnswers().add(buildAnswer(4, false));

        ChoiceQuestionDto response = post(PATH, dto, ChoiceQuestionDto.class);

        assertThat(response.getAnswers()).allMatch(ChoiceQuestionDto.Answer::getRight);
        for (int i = 0; i < response.getAnswers().size(); i++) {
            assertEquals(question.getChoices().get(i).getExplanationSourceCode(), response.getAnswers().get(i).getExplanationSource());
        }
        Long answerId = HashId.decodeHash(response.getAnswerId());
        SubmittedAnswer savedAnswer = submittedAnswerRepository.findById(answerId).orElseThrow();
        assertEquals(this.question.getId(), savedAnswer.getQuestion().getId());
        assertEquals(dto.getStudentId(), savedAnswer.getStudentId());
        assertTrue(savedAnswer.isFullScore());
        assertThat(savedAnswer.getChoices()).allMatch(SubmittedAnswer.Choice::isRight);
    }

    @Test
    public void testSubmittAllWrongQuestion() {

        ChoiceQuestionDto dto = new ChoiceQuestionDto();
        dto.setQuestionId(HashId.hash(question.getId()));
        dto.setStudentId("abc123");
        dto.setAnswers(new ArrayList<>());
        dto.getAnswers().add(buildAnswer(1, false));
        dto.getAnswers().add(buildAnswer(2, true));
        dto.getAnswers().add(buildAnswer(3, false));
        dto.getAnswers().add(buildAnswer(4, true));

        ChoiceQuestionDto response = post(PATH, dto, ChoiceQuestionDto.class);

        assertThat(response.getAnswers()).allMatch(i -> !i.getRight());
        for (int i = 0; i < response.getAnswers().size(); i++) {
            assertEquals(question.getChoices().get(i).getExplanationSourceCode(), response.getAnswers().get(i).getExplanationSource());
        }
        Long answerId = HashId.decodeHash(response.getAnswerId());
        SubmittedAnswer savedAnswer = submittedAnswerRepository.findById(answerId).orElseThrow();
        assertEquals(this.question.getId(), savedAnswer.getQuestion().getId());
        assertEquals(dto.getStudentId(), savedAnswer.getStudentId());
        assertFalse(savedAnswer.isFullScore());
        //assertThat(savedAnswer.getChoices()).allMatch(SubmittedAnswer.Choice::isRight);
    }

    private void setupQuestion() {
        Question question = new Question();
        question.setName("question 1");
        question.setEnabled(true);
        question.setSourceCode("foo bar");
        question.setSourceType(SourceType.MARKDOWN);
        question.setType(QuestionType.MULTI);
        question.setChoices(new ArrayList<>());
        question.getChoices().add(buildChoice(1, true));
        question.getChoices().add(buildChoice(2, false));
        question.getChoices().add(buildChoice(3, true));
        question.getChoices().add(buildChoice(4, false));
        this.question = questionRepository.save(question);
    }

    private Choice buildChoice(int id, boolean value) {
        Choice choice = new Choice();
        choice.setId(id);
        choice.setSourceCode("question " + id);
        choice.setValue(value);
        choice.setExplanationSourceCode("foo");
        return choice;
    }

    private ChoiceQuestionDto.Answer buildAnswer(int id, boolean selected) {
        ChoiceQuestionDto.Answer answer = new ChoiceQuestionDto.Answer();
        answer.setId(id);
        answer.setSelected(selected);
        return answer;
    }


}
