package br.com.pensarcomodev.service;

import br.com.pensarcomodev.microservices.codeeval.dto.AnswerCodeExecutionDto;
import br.com.pensarcomodev.microservices.codeeval.dto.AnswerCodeExecutionResponseDto;

import java.util.Optional;

public interface JavaSandboxIntegrationService {
    Optional<AnswerCodeExecutionResponseDto> submitCodeExecution(AnswerCodeExecutionDto dto);
}
