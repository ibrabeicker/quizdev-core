package br.com.pensarcomodev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Choice {

    private int id;

    private String sourceCode;

    private String explanationSourceCode;

    private boolean value;
}
