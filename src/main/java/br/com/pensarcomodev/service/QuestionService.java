package br.com.pensarcomodev.service;

import br.com.pensarcomodev.dto.QuestionDto;
import br.com.pensarcomodev.entity.Question;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

public interface QuestionService {

    QuestionDto findDtoById(Long id);

    Question findById(Long id);

    QuestionDto saveNew(QuestionDto questionDto);

    QuestionDto update(QuestionDto question);

    Page<QuestionDto> findAll(Pageable page);
}
