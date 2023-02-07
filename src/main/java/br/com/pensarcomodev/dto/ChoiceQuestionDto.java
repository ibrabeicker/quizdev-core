package br.com.pensarcomodev.dto;

import br.com.pensarcomodev.entity.enums.SourceType;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Introspected
public class ChoiceQuestionDto {

    private String questionId;

    private String studentId;

    private List<Answer> answers = new ArrayList<>();

    @Data
    @Introspected
    public static class Answer {

        private int id;

        private Boolean selected;

        private Boolean right;

        private String explanationSource;

        private SourceType sourceType;
    }

}
