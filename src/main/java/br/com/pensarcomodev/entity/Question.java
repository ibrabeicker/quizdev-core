package br.com.pensarcomodev.entity;

import br.com.pensarcomodev.entity.enums.QuestionType;
import io.micronaut.data.annotation.*;
import io.micronaut.data.jdbc.annotation.JoinColumn;
import io.micronaut.data.jdbc.annotation.JoinTable;
import io.micronaut.data.model.DataType;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static io.micronaut.data.annotation.Relation.Cascade.ALL;

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
    @MappedProperty(value = "choice_answers")
    @TypeDef(type = DataType.JSON)
    private List<ChoiceAnswer> choiceAnswers;

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

//    @MappedProperty(value = "metadata")
//    @TypeDef(type = DataType.JSON)
//    private QuestionMetadata metadata;

    @JoinTable(name = "question_tag_relation",
        inverseJoinColumns = {@JoinColumn(name = "id_question_tag")},
        joinColumns = {@JoinColumn(name = "id_question")}
    )
    @Relation(value = Relation.Kind.MANY_TO_MANY, cascade = ALL)
    private Set<QuestionTag> tags;
}
