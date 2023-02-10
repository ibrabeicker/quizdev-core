package br.com.pensarcomodev.entity;

import br.com.pensarcomodev.microservices.codeeval.dto.ProgrammingTestCase;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Introspected
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammingQuestion {

    private List<String> preExecutionCode;

    private List<ProgrammingTestCase> testCases = new ArrayList<>();

    private String codeResponse;
}
