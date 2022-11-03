package br.com.pensarcomodev.service;

import br.com.pensarcomodev.entity.Question;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

public interface QuestionService {

    Question findby(Long id);

    Question saveNew(Question question);

    Question update(Question question);

    Page<Question> findAll(Pageable page);
}
