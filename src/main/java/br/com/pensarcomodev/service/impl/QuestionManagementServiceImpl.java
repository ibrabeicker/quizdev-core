package br.com.pensarcomodev.service.impl;

import br.com.pensarcomodev.dto.QuestionDto;
import br.com.pensarcomodev.entity.Choice;
import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.mapper.QuestionMapper;
import br.com.pensarcomodev.repository.QuestionRepository;
import br.com.pensarcomodev.service.QuestionManagementService;
import br.com.pensarcomodev.service.QuestionTagService;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class QuestionManagementServiceImpl implements QuestionManagementService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuestionTagService questionTagService;

    @Override
    public QuestionDto findDtoById(Long id) {
        return toDto(findById(id));
    }

    @Override
    public Question findById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow();
        question.setTags(questionTagService.findTagByQuestionId(question.getId()));
        return question;
    }

    @Override
    public QuestionDto saveNew(QuestionDto questionDto) {
        Question question = fromDto(questionDto);
        question.setEnabled(true);
        setIds(question.getChoices());
        question = questionRepository.save(question);
        questionTagService.setTags(question);
        return toDto(question);
    }

    @Override
    public QuestionDto update(QuestionDto questionDto) {
        Question question = fromDto(questionDto);
        Question questionDb = findById(questionDto.getId());
        questionDb.setChoices(question.getChoices());
        questionDb.setProgrammingQuestion(question.getProgrammingQuestion());
        questionDb.setEnabled(question.isEnabled());
        questionDb.setSourceCode(question.getSourceCode());
        questionDb.setType(question.getType());
        questionDb.setTags(question.getTags());
        questionTagService.setTags(question);
        questionDb = questionRepository.update(questionDb);
        return toDto(questionDb);
    }

    @Override
    public Page<QuestionDto> findAll(Pageable page) {
        if (!page.isSorted()) {
            page = page.order(Sort.Order.asc("id"));
        }
        return fromPage(questionRepository.findByEnabled(true, page));
    }

    private QuestionDto toDto(Question question) {
        return questionMapper.toDto(question);
    }

    private Question fromDto(QuestionDto dto) {
        Question question = questionMapper.fromDto(dto);
        question.setTags(questionTagService.fromNames(dto.getTags()));
        return question;
    }

    private Page<QuestionDto> fromPage(Page<Question> page) {
        List<QuestionDto> collect = page.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return Page.of(collect, page.getPageable(), page.getTotalSize());
    }

    private void setIds(List<Choice> choices) {
        if (choices == null) {
            return;
        }
        for (int i = 0; i < choices.size(); i++) {
            choices.get(i).setId(i + 1);
        }
    }

}
