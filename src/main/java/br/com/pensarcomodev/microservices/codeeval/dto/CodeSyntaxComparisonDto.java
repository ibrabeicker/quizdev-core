package br.com.pensarcomodev.microservices.codeeval.dto;

import br.com.pensarcomodev.microservices.ServiceRequest;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Introspected
public class CodeSyntaxComparisonDto implements ServiceRequest {

    private String studentId;

    private String requestId;

    private String submittedCode;

    @ToString.Exclude
    private List<String> codeTemplates = new ArrayList<>();
}
