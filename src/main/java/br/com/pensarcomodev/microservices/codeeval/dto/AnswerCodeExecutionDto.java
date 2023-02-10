package br.com.pensarcomodev.microservices.codeeval.dto;

import br.com.pensarcomodev.microservices.ServiceRequest;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Introspected
public class AnswerCodeExecutionDto implements ServiceRequest {

    private String studentId;

    private String questionId;

    private String submittedCode;

    private String requestId;

    @ToString.Exclude
    private List<String> preExecutionCode;

    @ToString.Exclude
    private List<ProgrammingTestCase> testCases = new ArrayList<>();

}
