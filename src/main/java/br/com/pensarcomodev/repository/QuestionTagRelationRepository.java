package br.com.pensarcomodev.repository;

import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.entity.QuestionTag;
import br.com.pensarcomodev.entity.QuestionTagRelation;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface QuestionTagRelationRepository extends PageableRepository<QuestionTagRelation, Long> {

    @Executable
    @Query("Select qtr.* FROM question_tag_relation qtr where qtr.id_question = :questionId")
    List<QuestionTag> findTagByQuestionIdEquals(Long questionId);

    void deleteByQuestionEqualsAndTagInList(Question question, List<QuestionTag> tags);

    @Executable
    List<QuestionTag> findTagByQuestionEquals(Question question);
}
