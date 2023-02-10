package br.com.pensarcomodev.entity;

import br.com.pensarcomodev.microservices.codeeval.values.ExecutionStatus;
import io.micronaut.data.annotation.*;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@MappedEntity("submitted_programming_answer")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmittedProgrammingAnswer {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    @MappedProperty(value = "id_submitted_programming_answer")
    private Long id;

    @Relation(Relation.Kind.MANY_TO_ONE)
    @MappedProperty(value = "id_question")
    @ToString.Exclude
    private Question question;

    @MappedProperty(value = "id_student")
    private String studentId;

    @MappedProperty(value = "full_score")
    private Boolean fullScore;

    @MappedProperty(value = "submitted_code")
    private String submittedCode;

    @MappedProperty(value = "eval_status")
    private ExecutionStatus evalStatus;

    @MappedProperty(value = "error_message")
    private String errorMessage;

    @DateCreated
    @MappedProperty(value = "creation_date")
    @ToString.Exclude
    private ZonedDateTime creationDate;
}
