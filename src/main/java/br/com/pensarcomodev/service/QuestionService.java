package br.com.pensarcomodev.service;

import br.com.pensarcomodev.dto.QuestionDto;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

public interface QuestionService {

    QuestionDto findby(Long id);

    QuestionDto saveNew(QuestionDto questionDto);

    QuestionDto update(QuestionDto question);

    Page<QuestionDto> findAll(Pageable page);
}
