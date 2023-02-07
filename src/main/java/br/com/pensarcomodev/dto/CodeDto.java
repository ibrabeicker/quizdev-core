package br.com.pensarcomodev.dto;

import br.com.pensarcomodev.entity.enums.SourceType;
import lombok.Data;

@Data
public class CodeDto {

    private String sourceCode;

    private SourceType sourceType;
}
