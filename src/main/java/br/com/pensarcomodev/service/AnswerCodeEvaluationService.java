package br.com.pensarcomodev.service;

import br.com.pensarcomodev.microservices.codeeval.dto.AnswerCodeExecutionDto;
import br.com.pensarcomodev.microservices.codeeval.dto.AnswerCodeExecutionResponseDto;

public interface AnswerCodeEvaluationService {

    AnswerCodeExecutionResponseDto submitCodeForExecution(AnswerCodeExecutionDto dto);
}
