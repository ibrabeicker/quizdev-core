package br.com.pensarcomodev.sandbox.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnswerCodeExecutionDto {

    private String studentId;

    private String questionId;

    private String submittedCode;

    private List<String> preExecutionCode;

    private List<TestCase> testCases = new ArrayList<>();

    @Data
    public static class TestCase {

        private String verificationCode; // Chamada do código submetido que faz uma comparação

        private String callCode; // Chamada do código submetido a fim de exibir os parâmetros em casos de falha

        private Boolean passed;
    }
}
