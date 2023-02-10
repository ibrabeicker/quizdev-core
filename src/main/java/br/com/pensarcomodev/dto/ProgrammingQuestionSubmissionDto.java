package br.com.pensarcomodev.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Data
@Introspected
public class ProgrammingQuestionSubmissionDto {

    private String submittedCode;

    private String studentId;

    private String questionId;
}
