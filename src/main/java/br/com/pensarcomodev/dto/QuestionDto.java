package br.com.pensarcomodev.dto;

import br.com.pensarcomodev.entity.Choice;
import br.com.pensarcomodev.entity.CodeAnswer;
import br.com.pensarcomodev.entity.enums.QuestionType;
import br.com.pensarcomodev.entity.enums.SourceType;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@Introspected
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

    private Long id;

    private String name;

    private String sourceCode;

    private SourceType sourceType;

    private QuestionType type;

    private List<Choice> choices;

    private CodeAnswer codeAnswer;

    private ZonedDateTime creationDate;

    private boolean enabled;

    private List<String> tags;
}
