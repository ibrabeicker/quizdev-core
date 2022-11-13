package br.com.pensarcomodev.repository;

import br.com.pensarcomodev.entity.Question;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface QuestionRepository extends PageableRepository<Question, Long> {

    @Executable
    @Override
    Optional<Question> findById(@NotNull Long aLong);

    Page<Question> findByEnabled(boolean enabled, Pageable page);

}
