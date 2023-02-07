package br.com.pensarcomodev.service;

import br.com.pensarcomodev.sandbox.dto.AnswerCodeExecutionDto;
import br.com.pensarcomodev.sandbox.dto.AnswerCodeExecutionResponseDto;

public interface AnswerCodeEvaluationService {

    AnswerCodeExecutionResponseDto submitCodeForExecution(AnswerCodeExecutionDto dto);
}
