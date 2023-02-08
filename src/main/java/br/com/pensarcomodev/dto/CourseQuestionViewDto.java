package br.com.pensarcomodev.dto;

import br.com.pensarcomodev.entity.enums.QuestionType;
import br.com.pensarcomodev.entity.enums.SourceType;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.List;

@Data
@Introspected
public class CourseQuestionViewDto {

    private String questionId;

    private String sourceCode;

    private SourceType sourceType;

    private QuestionType type;

    private List<Choice> choices;

    @Data
    @Introspected
    public static class Choice {

        private int id;

        private String sourceCode;
    }

}
