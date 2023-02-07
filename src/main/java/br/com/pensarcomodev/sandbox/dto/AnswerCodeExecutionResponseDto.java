package br.com.pensarcomodev.sandbox.dto;

import br.com.pensarcomodev.sandbox.EvalStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnswerCodeExecutionResponseDto {

    private String studentId;

    private String submittedCode;

    private EvalStatus evalStatus;

    private List<AnswerCodeExecutionDto.TestCase> testCases = new ArrayList<>();
}
