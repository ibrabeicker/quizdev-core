package br.com.pensarcomodev.controller;

import br.com.pensarcomodev.dto.QuestionDto;
import br.com.pensarcomodev.service.impl.QuestionServiceImpl;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionServiceImpl questionService;

    @Get("/{id}")
    public QuestionDto findById(Long id) {
        return questionService.findDtoById(id);
    }

    @Get
    public Page<QuestionDto> listQuestions(Pageable page) {
        return questionService.findAll(page);
    }

    @Post
    public QuestionDto saveNew(@Body QuestionDto questionDto) {
        return questionService.saveNew(questionDto);
    }

    @Patch
    public QuestionDto update(@Body QuestionDto questionDto) {
        return questionService.update(questionDto);
    }
}
