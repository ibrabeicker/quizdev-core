package br.com.pensarcomodev.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ChoiceQuestionSubmissionDto {

    private Long questionId;

    private Map<Integer, Boolean> selected = new HashMap<>();

}
