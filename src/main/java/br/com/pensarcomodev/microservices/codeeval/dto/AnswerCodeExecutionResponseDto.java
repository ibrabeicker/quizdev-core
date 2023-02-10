package br.com.pensarcomodev.microservices.codeeval.dto;

import br.com.pensarcomodev.microservices.codeeval.values.ExecutionStatus;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Introspected
public class AnswerCodeExecutionResponseDto {

    private String studentId;

    private String submittedCode;

    private ExecutionStatus evalStatus;

    private List<ProgrammingTestCaseResult> testCases = new ArrayList<>();

    private String errorMessage;

    @Data
    @Introspected
    public static class ProgrammingTestCaseResult {

        private ProgrammingTestCase testCase;

        private boolean passed;
    }

    public boolean isFullScore() {
        return testCases.stream().allMatch(i -> i.isPassed());
    }

}
