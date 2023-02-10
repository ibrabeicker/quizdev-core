package br.com.pensarcomodev.service.impl;

import br.com.pensarcomodev.client.JavaSandboxClient;
import br.com.pensarcomodev.microservices.codeeval.dto.AnswerCodeExecutionDto;
import br.com.pensarcomodev.microservices.codeeval.dto.AnswerCodeExecutionResponseDto;
import br.com.pensarcomodev.service.JavaSandboxIntegrationService;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.exceptions.HttpException;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class JavaSandboxIntegrationServiceImpl implements JavaSandboxIntegrationService {

    private final JavaSandboxClient client;

    @Override
    public Optional<AnswerCodeExecutionResponseDto> submitCodeExecution(AnswerCodeExecutionDto dto) {
        try {
            return Optional.of(client.executeCode(dto));
        } catch (HttpClientResponseException e) {
            log.warn("{}", e.getStatus());
            return Optional.empty();
        } catch (HttpException e) {
            log.warn("{}", e.getMessage());
            return Optional.empty();
        }
    }
}
