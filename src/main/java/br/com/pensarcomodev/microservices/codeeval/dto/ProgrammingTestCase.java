package br.com.pensarcomodev.microservices.codeeval.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Data
@Introspected
public class ProgrammingTestCase {

    private String verificationCode; // Chamada do código submetido que faz uma comparação

    private String callCode; // Chamada do código submetido a fim de exibir os parâmetros em casos de falha
}
