package br.com.pensarcomodev.dto;

import br.com.pensarcomodev.entity.Choice;
import br.com.pensarcomodev.entity.ChoiceAnswer;
import br.com.pensarcomodev.entity.CodeAnswer;
import br.com.pensarcomodev.entity.QuestionMetadata;
import br.com.pensarcomodev.entity.enums.QuestionType;
import br.com.pensarcomodev.entity.enums.SourceType;
import io.micronaut.data.annotation.*;
import io.micronaut.data.model.DataType;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
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
