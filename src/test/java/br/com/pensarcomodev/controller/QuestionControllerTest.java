package br.com.pensarcomodev.controller;

import br.com.pensarcomodev.entity.ChoiceAnswer;
import br.com.pensarcomodev.entity.CodeAnswer;
import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.entity.enums.QuestionType;
import br.com.pensarcomodev.repository.QuestionRepository;
import io.micronaut.data.model.Page;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuestionControllerTest extends AbstractControllerTest {

    private static Long savedId;

    @Inject
    QuestionRepository questionRepository;

    @Test
    @Order(1)
    public void testSaveQuestion() {
        Question question = buildQuestion();
        Question response = post("/question", question, Question.class);
        savedId = response.getId();
        assertNotNull(response.getId());
    }

    @Test
    @Order(2)
    public void testFindById() {
        assertNotNull(savedId);
        Question response = get("/question/" + savedId, Question.class);
        assertNotNull(response);
        assertEquals(2, response.getChoiceAnswers().size());
    }

    @Test
    @Order(3)
    public void testPagination() {

        questionRepository.deleteAll();
        persistQuestions(20);

        Page<Question> response = getWithPage("/question", 0, 10, Question.class);
        assertEquals(10, response.getContent().size());
        response = getWithPage("/question", 1, 10, Question.class);
        assertEquals(10, response.getContent().size());
        response = getWithPage("/question", 2, 10, Question.class);
        assertEquals(0, response.getContent().size());
        assertEquals(20, response.getTotalSize());
    }

    @Test
    @Order(4)
    public void testWithoutPagination() {

        assertEquals(20, questionRepository.count());
        Page<Question> response = getWithPage("/question", Question.class);
        assertEquals(10, response.getContent().size());
    }

    @Test
    @Order(5)
    public void testCodeAnswerSave() {

        Question question = buildCodeQuestion();
        Question response = post("/question", question, Question.class);

        assertNull(response.getChoiceAnswers());
        assertNotNull(response.getCodeAnswer());
        assertEquals(QuestionType.CODE, response.getType());
    }

    @Test
    @Order(6)
    public void testUpdateQuestion() {

        Question response = post("/question", buildQuestion(), Question.class);
        Question question = get("/question/" + response.getId(), Question.class);

        // Quando atualiza as informações
        ZonedDateTime now = ZonedDateTime.now(Clock.systemUTC());
        question.setText("Novo texto");
        question.setCreationDate(now);
        question.setName("Novo nome");
        question.getChoiceAnswers().get(0).setText("Nova resposta 2");
        patch("/question", question, Question.class);

        response = get("/question/" + response.getId(), Question.class);

        // As propriedades permanentes não devem ser sobrescritas
        assertNotEquals("Novo nome", response.getName());
        assertNotEquals(now, response.getCreationDate());

        // O resto das propriedades é sobrescrita
        assertEquals("Novo texto", response.getText());
        assertEquals("Nova resposta 2", response.getChoiceAnswers().get(0).getText());
        assertEquals(2, response.getChoiceAnswers().size());
        assertEquals("Alternative 2", response.getChoiceAnswers().get(1).getText());
    }

    @Test
    @Order(6)
    public void testDisableQuestion_doesNotShowInPagination() {

        questionRepository.deleteAll();
        Question response = post("/question", buildQuestion(), Question.class);
        Question question = get("/question/" + response.getId(), Question.class);

        // Quando desativar a pergunta
        question.setEnabled(false);
        patch("/question", question, Question.class);

        // Aparece quando busca por id
        response = get("/question/" + response.getId(), Question.class);
        assertNotNull(response);
        assertFalse(response.isEnabled());

        // Não aparece quando busca por paginação
        Page<Question> pagedResponse = getWithPage("/question", 0, 10, Question.class);
        assertThat(pagedResponse.getContent()).isEmpty();
    }

    private Question buildQuestion() {
        return Question.builder()
                .name("pergunta 1")
                .text("Pergunta 1?")
                .type(QuestionType.UNICO)
                .choiceAnswers(buildAnswers())
                .build();
    }

    private Question buildCodeQuestion() {
        return Question.builder()
                .name("pergunta 2")
                .text("Pergunta 2?")
                .type(QuestionType.CODE)
                .codeAnswer(CodeAnswer.builder().code("foo bar").build())
                .build();
    }

    private List<ChoiceAnswer> buildAnswers() {
        return Arrays.asList(
                ChoiceAnswer.builder()
                        .right(true)
                        .text("Alternative 1")
                        .build(),
                ChoiceAnswer.builder()
                        .right(false)
                        .text("Alternative 2")
                        .build()
        );
    }

    private void persistQuestions(int number) {
        for (int i = 1; i <= number; i++) {
            Question question = Question.builder()
                    .type(QuestionType.UNICO)
                    .name("pergunta_" + i)
                    .text("Pergunta " + i)
                    .choiceAnswers(buildAnswers())
                    .build();
            post("/question", question, Question.class);
        }
    }
}
