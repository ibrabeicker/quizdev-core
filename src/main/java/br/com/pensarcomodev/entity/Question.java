package br.com.pensarcomodev.entity;

import br.com.pensarcomodev.entity.enums.QuestionType;
import io.micronaut.data.annotation.*;
import io.micronaut.data.model.DataType;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
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

    @MappedProperty(value = "text")
    private String text;

    @ToString.Exclude
    @MappedProperty(value = "type")
    private QuestionType type;

    @ToString.Exclude
    @MappedProperty(value = "answers")
    @TypeDef(type = DataType.JSON)
    private List<Answer> answers = new ArrayList<>();

    @DateCreated
    @MappedProperty(value = "creation_date")
    @ToString.Exclude
    private Instant creationDate;
}
