package br.com.pensarcomodev.dto;

import br.com.pensarcomodev.entity.ChoiceAnswer;
import br.com.pensarcomodev.entity.CodeAnswer;
import br.com.pensarcomodev.entity.QuestionMetadata;
import br.com.pensarcomodev.entity.enums.QuestionType;
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

    private String text;

    private QuestionType type;

    private List<ChoiceAnswer> choiceAnswers;

    private CodeAnswer codeAnswer;

    private ZonedDateTime creationDate;

    private boolean enabled;

    private List<String> tags;
}
