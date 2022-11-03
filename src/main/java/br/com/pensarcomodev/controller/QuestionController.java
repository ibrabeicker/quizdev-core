package br.com.pensarcomodev.controller;

import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.service.impl.QuestionServiceImpl;
import io.micronaut.context.ApplicationContext;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/question")
public class QuestionController {

    @Inject
    private QuestionServiceImpl questionService;

    @Get("/{id}")
    public Question findById(Long id) {
        return questionService.findby(id);
    }

    @Inject
    ApplicationContext applicationContext;

    @Get
    public Page<Question> listQuestions(Pageable page) {
        return questionService.findAll(page);
    }

    @Post
    public Question saveNew(@Body Question question) {
        return questionService.saveNew(question);
    }

    @Patch
    public Question update(@Body Question question) {
        return questionService.update(question);
    }
}
