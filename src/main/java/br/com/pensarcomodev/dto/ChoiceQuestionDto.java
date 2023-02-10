package br.com.pensarcomodev.dto;

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

    private String answerId;

    @Data
    @Introspected
    public static class Answer {

        private int id;

        private Boolean selected;

        private Boolean right;

        private String explanationSource;

    }

}
