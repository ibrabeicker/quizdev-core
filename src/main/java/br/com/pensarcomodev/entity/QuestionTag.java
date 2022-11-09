package br.com.pensarcomodev.entity;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedEntity("question_tag")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTag {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    @MappedProperty(value = "id_question_tag")
    private Integer id;

    @MappedProperty(value = "name")
    private String name;
}
