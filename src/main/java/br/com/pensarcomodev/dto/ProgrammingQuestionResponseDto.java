package br.com.pensarcomodev.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Introspected
public class ProgrammingQuestionResponseDto {

    private List<String> failingTestCases = new ArrayList<>();

    private boolean success;
}
