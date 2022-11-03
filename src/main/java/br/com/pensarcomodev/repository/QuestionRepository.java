package br.com.pensarcomodev.repository;

import br.com.pensarcomodev.entity.Question;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface QuestionRepository extends PageableRepository<Question, Long> {

    Page<Question> findByEnabled(boolean enabled, Pageable page);
}
