package br.com.pensarcomodev.dto;

import br.com.pensarcomodev.entity.enums.SourceType;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Data
@Introspected
public class CodeContentDto {

    private String sourceCode;

    private SourceType sourceType;
}
