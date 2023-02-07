package br.com.pensarcomodev.entity;

import br.com.pensarcomodev.entity.enums.QuestionType;
import br.com.pensarcomodev.entity.enums.SourceType;
import io.micronaut.data.annotation.*;
import io.micronaut.data.model.DataType;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@MappedEntity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    @MappedProperty(value = "id_question")
    private Long id;

    @MappedProperty(value = "name")
    private String name;

    @MappedProperty(value = "source_code")
    private String sourceCode;

    @MappedProperty(value = "source_type")
    private SourceType sourceType;

    @ToString.Exclude
    @MappedProperty(value = "type")
    private QuestionType type;

    @ToString.Exclude
    @MappedProperty(value = "choices")
    @TypeDef(type = DataType.JSON)
    private List<Choice> choices;

    @ToString.Exclude
    @MappedProperty(value = "code_answer")
    @TypeDef(type = DataType.JSON)
    private CodeAnswer codeAnswer;

    @DateCreated
    @MappedProperty(value = "creation_date")
    @ToString.Exclude
    private ZonedDateTime creationDate;

    @ToString.Exclude
    @MappedProperty(value = "enabled")
    private boolean enabled;

    @Transient
    private List<QuestionTag> tags;
}
