package br.com.pensarcomodev.microservices.codeeval.values;

public enum ExecutionStatus {

    DECLARATION,
    RESULT,
    INVALID_SYNTAX,
    EXECUTION_ERROR,
    POSSIBLE_LOOP,

    // Resposta
    MALCONFIGURED_QUESTION
}
