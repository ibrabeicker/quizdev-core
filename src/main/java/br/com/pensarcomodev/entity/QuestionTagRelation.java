package br.com.pensarcomodev.entity;

import io.micronaut.data.annotation.*;
import io.micronaut.data.jdbc.annotation.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedEntity("question_tag_relation")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTagRelation {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    @MappedProperty(value = "id_question_tag_relation")
    private Integer id;

    @Relation(Relation.Kind.MANY_TO_ONE)
    @MappedProperty(value = "id_question")
    private Question question;

    @Relation(Relation.Kind.MANY_TO_ONE)
    @MappedProperty(value = "id_question_tag")
    private QuestionTag tag;
}
