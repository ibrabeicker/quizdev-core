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
    private Long id;

    @Relation(Relation.Kind.MANY_TO_ONE)
    @MappedProperty(value = "id_question")
    @ToString.Exclude
    private Question question;

    @MappedProperty(value = "id_student")
    private String studentId;

    @MappedProperty(value = "full_score")
    private boolean fullScore;

    @MappedProperty(value = "choices")
    @TypeDef(type = DataType.JSON)
    @ToString.Exclude
    private List<Choice> choices = new ArrayList<>();

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

    @ToString.Include(name = "questionId")
    public Long questionId() {
        return question != null ? question.getId() : null;
    }
}
