package br.com.pensarcomodev.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ChoiceQuestionResultDto {

    private Long questionId;

    private Map<Integer, Boolean> selected = new HashMap<>();

    private Map<Integer, Boolean> response = new HashMap<>();

    @Data
    public static class Answer {

        private
    }
}
