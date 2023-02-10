package br.com.pensarcomodev.client;

import br.com.pensarcomodev.microservices.codeeval.dto.AnswerCodeExecutionDto;
import br.com.pensarcomodev.microservices.codeeval.dto.AnswerCodeExecutionResponseDto;
import br.com.pensarcomodev.microservices.codeeval.dto.CodeSyntaxComparisonDto;
import br.com.pensarcomodev.microservices.codeeval.dto.CodeSyntaxComparisonResponseDto;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client(id = "java-sandbox")
public interface JavaSandboxClient {

    @Post("/eval/code")
    AnswerCodeExecutionResponseDto executeCode(AnswerCodeExecutionDto dto);

    @Post("/eval/syntax")
    CodeSyntaxComparisonResponseDto compareCode(CodeSyntaxComparisonDto dto);
}
