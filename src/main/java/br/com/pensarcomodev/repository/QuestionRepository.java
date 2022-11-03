package br.com.pensarcomodev.repository;

import br.com.pensarcomodev.entity.Question;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface QuestionRepository extends PageableRepository<Question, Long> {

}
