package br.com.pensarcomodev.service.impl;

import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.exception.IdNotFoundException;
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
    public Question saveNew(Question question) {
        question.setEnabled(true);
        return questionRepository.save(question);
    }

    @Override
    public Question update(Question question) {
        Question questionDb = questionRepository.findById(question.getId()).orElseThrow();
        questionDb.setChoiceAnswers(question.getChoiceAnswers());
        questionDb.setCodeAnswer(question.getCodeAnswer());
        questionDb.setEnabled(question.isEnabled());
        questionDb.setText(question.getText());
        questionDb.setType(question.getType());
        return questionRepository.update(questionDb);
    }

    @Override
    public Page<Question> findAll(Pageable page) {
        if (!page.isSorted()) {
            page = page.order(Sort.Order.asc("id"));
        }
        return questionRepository.findByEnabled(true, page);
    }
}
