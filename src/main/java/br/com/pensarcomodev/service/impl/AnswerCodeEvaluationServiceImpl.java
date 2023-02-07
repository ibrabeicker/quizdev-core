package br.com.pensarcomodev.service.impl;

import br.com.pensarcomodev.microservices.codeeval.dto.AnswerCodeExecutionDto;
import br.com.pensarcomodev.microservices.codeeval.dto.AnswerCodeExecutionResponseDto;
import br.com.pensarcomodev.service.AnswerCodeEvaluationService;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class AnswerCodeEvaluationServiceImpl implements AnswerCodeEvaluationService {

    @Override
    public AnswerCodeExecutionResponseDto submitCodeForExecution(AnswerCodeExecutionDto dto) {
        return null;
    }
}
