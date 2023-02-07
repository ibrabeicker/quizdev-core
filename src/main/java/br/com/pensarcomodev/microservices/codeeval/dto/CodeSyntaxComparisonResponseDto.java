package br.com.pensarcomodev.microservices.codeeval.dto;

import br.com.pensarcomodev.microservices.ServiceRequest;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Data
@Introspected
public class CodeSyntaxComparisonResponseDto implements ServiceRequest {

    private String studentId;

    private String requestId;

    private boolean success;

    public CodeSyntaxComparisonResponseDto(CodeSyntaxComparisonDto dto, boolean success) {
        this.studentId = dto.getStudentId();
        this.requestId = dto.getRequestId();
        this.success = success;
    }
}
