package br.com.pensarcomodev;

import br.com.pensarcomodev.controller.AbstractControllerTest;
import br.com.pensarcomodev.entity.Answer;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        assertEquals(2, response.getAnswers().size());
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

    private Question buildQuestion() {
        return Question.builder()
                .name("pergunta 1")
                .text("Pergunta 1?")
                .type(QuestionType.UNICO)
                .answers(buildAnswers())
                .build();
    }

    private List<Answer> buildAnswers() {
        return Arrays.asList(
                Answer.builder()
                        .right(true)
                        .text("Alternative 1")
                        .build(),
                Answer.builder()
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
                    .answers(buildAnswers())
                    .build();
            post("/question", question, Question.class);
        }
    }
}
