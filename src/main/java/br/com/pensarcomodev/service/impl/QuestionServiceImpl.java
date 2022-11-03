package br.com.pensarcomodev.service.impl;

import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.repository.QuestionRepository;
import br.com.pensarcomodev.service.QuestionService;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import jakarta.inject.Inject;

public class QuestionServiceImpl implements QuestionService {

    @Inject
    private QuestionRepository questionRepository;

    @Override
    public Question findby(Long id) {
        return questionRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Page<Question> findAll(Pageable page) {
        if (!page.isSorted()) {
            page = page.order(Sort.Order.asc("id"));
        }
        return questionRepository.findAll(page);
    }
}
