package br.com.pensarcomodev.controller;

import br.com.pensarcomodev.dto.QuestionDto;
import br.com.pensarcomodev.entity.ChoiceAnswer;
import br.com.pensarcomodev.entity.CodeAnswer;
import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.entity.enums.QuestionType;
import br.com.pensarcomodev.repository.QuestionRepository;
import br.com.pensarcomodev.repository.QuestionTagRepository;
import io.micronaut.data.model.Page;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuestionControllerTest extends AbstractControllerTest {

    private Long savedId;

    @Inject
    QuestionRepository questionRepository;

    @Inject
    QuestionTagRepository questionTagRepository;

    @BeforeEach
    public void setup() {
        questionRepository.deleteAll();
        questionTagRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testSaveQuestion() {
        QuestionDto question = buildQuestion();
        QuestionDto createdResponse = post("/question", question, QuestionDto.class);
        savedId = createdResponse.getId();
        assertNotNull(savedId);

        QuestionDto getResponse = get("/question/" + savedId, QuestionDto.class);
        assertNotNull(getResponse);
        assertEquals(2, getResponse.getChoiceAnswers().size());
        assertThat(getResponse.getTags()).contains("java", "easy");
    }

    @Test
    @Order(2)
    public void testPagination() {

        persistQuestions(20);

        Page<QuestionDto> response = getWithPage("/question", 0, 10, QuestionDto.class);
        assertEquals(10, response.getContent().size());
        response = getWithPage("/question", 1, 10, QuestionDto.class);
        assertEquals(10, response.getContent().size());
        response = getWithPage("/question", 2, 10, QuestionDto.class);
        assertEquals(0, response.getContent().size());
        assertEquals(20, response.getTotalSize());
    }

    @Test
    @Order(3)
    public void testWithoutPagination() {

        persistQuestions(20);

        Page<QuestionDto> response = getWithPage("/question", QuestionDto.class);
        assertEquals(10, response.getContent().size());
    }

    @Test
    @Order(4)
    public void testCodeAnswerSave() {

        QuestionDto question = buildCodeQuestion();
        QuestionDto response = post("/question", question, QuestionDto.class);

        assertNull(response.getChoiceAnswers());
        assertNotNull(response.getCodeAnswer());
        assertEquals(QuestionType.CODE, response.getType());
    }

    @Test
    @Order(5)
    public void testUpdateQuestion() {

        QuestionDto response = post("/question", buildQuestion(), QuestionDto.class);
        QuestionDto question = get("/question/" + response.getId(), QuestionDto.class);

        // Quando atualiza as informações
        ZonedDateTime now = ZonedDateTime.now(Clock.systemUTC());
        question.setText("Novo texto");
        question.setCreationDate(now);
        question.setName("Novo nome");
        question.getChoiceAnswers().get(0).setText("Nova resposta 2");
        patch("/question", question, QuestionDto.class);

        response = get("/question/" + response.getId(), QuestionDto.class);

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
        QuestionDto response = post("/question", buildQuestion(), QuestionDto.class);
        QuestionDto question = get("/question/" + response.getId(), QuestionDto.class);

        // Quando desativar a pergunta
        question.setEnabled(false);
        patch("/question", question, QuestionDto.class);

        // Aparece quando busca por id
        response = get("/question/" + response.getId(), QuestionDto.class);
        assertNotNull(response);
        assertFalse(response.isEnabled());

        // Não aparece quando busca por paginação
        Page<QuestionDto> pagedResponse = getWithPage("/question", 0, 10, QuestionDto.class);
        assertThat(pagedResponse.getContent()).isEmpty();
    }

    private QuestionDto buildQuestion() {
        return QuestionDto.builder()
                .name("pergunta 1")
                .text("Pergunta 1?")
                .type(QuestionType.UNICO)
                .choiceAnswers(buildAnswers())
                .tags(List.of("java", "easy"))
                .build();
    }

    private QuestionDto buildCodeQuestion() {
        return QuestionDto.builder()
                .name("pergunta 2")
                .text("Pergunta 2?")
                .type(QuestionType.CODE)
                .tags(List.of("java", "easy"))
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
            QuestionDto question = QuestionDto.builder()
                    .type(QuestionType.UNICO)
                    .name("pergunta_" + i)
                    .text("Pergunta " + i)
                    .choiceAnswers(buildAnswers())
                    .tags(List.of("java", "easy"))
                    .build();
            post("/question", question, QuestionDto.class);
        }
    }
}
