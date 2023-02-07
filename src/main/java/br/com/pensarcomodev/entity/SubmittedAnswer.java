package br.com.pensarcomodev.entity;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.*;
import io.micronaut.data.model.DataType;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@MappedEntity("submitted_answer")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmittedAnswer {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    @MappedProperty(value = "id_submitted_answer")
    public Long id;

    @Relation(Relation.Kind.MANY_TO_ONE)
    @MappedProperty(value = "id_question")
    public Question question;

    @MappedProperty(value = "id_student")
    public String studentId;

    @MappedProperty(value = "full_score")
    public boolean fullScore;

    @MappedProperty(value = "choices")
    @TypeDef(type = DataType.JSON)
    public List<Choice> choices = new ArrayList<>();

    @DateCreated
    @MappedProperty(value = "creation_date")
    @ToString.Exclude
    private ZonedDateTime creationDate;

    @Data
    @Introspected
    public static class Choice {

        private int id;

        private boolean right;
    }

    public Long questionId() {
        return question != null ? question.getId() : null;
    }
}
