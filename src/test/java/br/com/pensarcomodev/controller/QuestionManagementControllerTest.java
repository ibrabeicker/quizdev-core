package br.com.pensarcomodev.controller;

import br.com.pensarcomodev.dto.QuestionDto;
import br.com.pensarcomodev.entity.Choice;
import br.com.pensarcomodev.entity.CodeAnswer;
import br.com.pensarcomodev.entity.enums.QuestionType;
import br.com.pensarcomodev.entity.enums.SourceType;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuestionManagementControllerTest extends AbstractControllerTest {

    private static final String PATH = "/question-management";
    private static final String PATH_PREFIX = PATH + "/";
    
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
        QuestionDto createdResponse = post(PATH, question, QuestionDto.class);
        savedId = createdResponse.getId();
        assertNotNull(savedId);

        QuestionDto getResponse = get(PATH_PREFIX + savedId, QuestionDto.class);
        assertNotNull(getResponse);
        assertEquals(SourceType.MARKDOWN, getResponse.getSourceType());
        assertEquals(2, getResponse.getChoices().size());
        assertThat(getResponse.getTags()).contains("java", "easy");
    }

    @Test
    @Order(2)
    public void testPagination() {

        persistQuestions(20);

        Page<QuestionDto> response = getWithPage(PATH, 0, 10, QuestionDto.class);
        assertEquals(10, response.getContent().size());
        response = getWithPage(PATH, 1, 10, QuestionDto.class);
        assertEquals(10, response.getContent().size());
        response = getWithPage(PATH, 2, 10, QuestionDto.class);
        assertEquals(0, response.getContent().size());
        assertEquals(20, response.getTotalSize());
    }

    @Test
    @Order(3)
    public void testWithoutPagination() {

        persistQuestions(20);

        Page<QuestionDto> response = getWithPage(PATH, QuestionDto.class);
        assertEquals(10, response.getContent().size());
    }

    @Test
    @Order(4)
    public void testCodeAnswerSave() {

        QuestionDto question = buildCodeQuestion();
        QuestionDto response = post(PATH, question, QuestionDto.class);

        assertNull(response.getChoices());
        assertNotNull(response.getCodeAnswer());
        assertEquals(QuestionType.CODE, response.getType());
    }

    @Test
    @Order(5)
    public void testUpdateQuestion() {

        QuestionDto response = post(PATH, buildQuestion(), QuestionDto.class);
        QuestionDto question = get(PATH_PREFIX + response.getId(), QuestionDto.class);

        // Quando atualiza as informações
        ZonedDateTime now = ZonedDateTime.now(Clock.systemUTC());
        question.setSourceCode("Novo texto");
        question.setCreationDate(now);
        question.setName("Novo nome");
        question.getChoices().get(0).setSourceCode("Nova resposta 2");
        patch(PATH, question, QuestionDto.class);

        response = get(PATH_PREFIX + response.getId(), QuestionDto.class);

        // As propriedades permanentes não devem ser sobrescritas
        assertNotEquals("Novo nome", response.getName());
        assertNotEquals(now, response.getCreationDate());

        // O resto das propriedades é sobrescrita
        assertEquals("Novo texto", response.getSourceCode());
        assertEquals("Nova resposta 2", response.getChoices().get(0).getSourceCode());
        assertEquals(2, response.getChoices().size());
        assertEquals("Alternative 2", response.getChoices().get(1).getSourceCode());
    }

    @Test
    @Order(6)
    public void testDisableQuestion_doesNotShowInPagination() {

        QuestionDto response = post(PATH, buildQuestion(), QuestionDto.class);
        QuestionDto question = get(PATH_PREFIX + response.getId(), QuestionDto.class);

        // Quando desativar a pergunta
        question.setEnabled(false);
        patch(PATH, question, QuestionDto.class);

        // Aparece quando busca por id
        response = get(PATH_PREFIX + response.getId(), QuestionDto.class);
        assertNotNull(response);
        assertFalse(response.isEnabled());

        // Não aparece quando busca por paginação
        Page<QuestionDto> pagedResponse = getWithPage(PATH, 0, 10, QuestionDto.class);
        assertThat(pagedResponse.getContent()).isEmpty();
    }

    @Test
    @Order(7)
    public void testChangeQuestionTags() {
        QuestionDto response = post(PATH, buildQuestion(), QuestionDto.class);
        savedId = response.getId();
        QuestionDto question = get(PATH_PREFIX + savedId, QuestionDto.class);

        question.setTags(List.of("java", "medium"));
        patch(PATH, question, QuestionDto.class);

        QuestionDto question2 = get(PATH_PREFIX + savedId, QuestionDto.class);
        assertThat(question2.getTags()).contains("java", "medium");
        assertThat(question2.getTags()).doesNotContain("easy");
    }

    private QuestionDto buildQuestion() {
        return QuestionDto.builder()
                .name("pergunta 1")
                .sourceCode("Pergunta 1?")
                .sourceType(SourceType.MARKDOWN)
                .type(QuestionType.UNICO)
                .choices(buildAnswers())
                .tags(List.of("java", "easy"))
                .build();
    }

    private QuestionDto buildCodeQuestion() {
        return QuestionDto.builder()
                .name("pergunta 2")
                .sourceCode("Pergunta 2?")
                .sourceType(SourceType.MARKDOWN)
                .type(QuestionType.CODE)
                .tags(List.of("java", "easy"))
                .codeAnswer(CodeAnswer.builder().code("foo bar").build())
                .build();
    }

    private List<Choice> buildAnswers() {
        return Arrays.asList(
                Choice.builder()
                        .value(true)
                        .sourceCode("Alternative 1")
                        .sourceType(SourceType.MARKDOWN)
                        .build(),
                Choice.builder()
                        .value(false)
                        .sourceCode("Alternative 2")
                        .build()
        );
    }

    private void persistQuestions(int number) {
        for (int i = 1; i <= number; i++) {
            QuestionDto question = QuestionDto.builder()
                    .type(QuestionType.UNICO)
                    .name("pergunta_" + i)
                    .sourceCode("Pergunta " + i)
                    .sourceType(SourceType.MARKDOWN)
                    .choices(buildAnswers())
                    .tags(List.of("java", "easy"))
                    .build();
            post(PATH, question, QuestionDto.class);
        }
    }
}
