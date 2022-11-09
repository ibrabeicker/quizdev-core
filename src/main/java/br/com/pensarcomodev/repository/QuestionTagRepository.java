package br.com.pensarcomodev.repository;

import br.com.pensarcomodev.entity.QuestionTag;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface QuestionTagRepository extends PageableRepository<QuestionTag, Long> {

    List<QuestionTag> findByNameInList(List<String> names);

    List<QuestionTag> findByIdInList(List<Integer> ids);
}
