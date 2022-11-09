package br.com.pensarcomodev.service.impl;

import br.com.pensarcomodev.dto.QuestionDto;
import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.entity.QuestionMetadata;
import br.com.pensarcomodev.mapper.QuestionMapper;
import br.com.pensarcomodev.repository.QuestionRepository;
import br.com.pensarcomodev.service.QuestionService;
import br.com.pensarcomodev.service.QuestionTagService;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuestionTagService questionTagService;

    @Override
    public QuestionDto findby(Long id) {
        return toDto(questionRepository.findById(id).orElseThrow());
    }

    @Override
    public QuestionDto saveNew(QuestionDto questionDto) {
        Question question = fromDto(questionDto);
        question.setEnabled(true);
        question = questionRepository.save(question);
        return toDto(question);
    }

    @Override
    public QuestionDto update(QuestionDto questionDto) {
        Question question = fromDto(questionDto);
        Question questionDb = questionRepository.findById(question.getId()).orElseThrow();
        questionDb.setChoiceAnswers(question.getChoiceAnswers());
        questionDb.setCodeAnswer(question.getCodeAnswer());
        questionDb.setEnabled(question.isEnabled());
        questionDb.setText(question.getText());
        questionDb.setType(question.getType());
        questionDb.setMetadata(question.getMetadata());
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
        QuestionDto dto = questionMapper.toDto(question);
        dto.setTags(buildTagsString(question.getMetadata().getTags()));
        return dto;
    }

    private QuestionDto toDto(Question question, Map<Integer, String> idsToName) {
        QuestionDto dto = questionMapper.toDto(question);
        dto.setTags(buildTagsString(question.getMetadata().getTags(), idsToName));
        return dto;
    }

    private Question fromDto(QuestionDto dto) {
        Question question = questionMapper.fromDto(dto);
        question.setMetadata(buildMetadata(dto));
        return question;
    }

    private QuestionMetadata buildMetadata(QuestionDto questionDto) {
        QuestionMetadata questionMetadata = new QuestionMetadata();
        List<String> tags = Arrays.asList(questionDto.getTags().split(" "));
        questionMetadata.setTags(questionTagService.findIdsByNames(tags));
        return questionMetadata;
    }

    private String buildTagsString(List<Integer> tagsIds, Map<Integer, String> idsToName) {
        return tagsIds.stream().map(idsToName::get).sorted()
                .collect(Collectors.joining(" "));
    }

    private String buildTagsString(List<Integer> tagsIds) {
        List<String> tagsNames = questionTagService.findNamesByIds(tagsIds);
        return tagsNames.stream().sorted().collect(Collectors.joining(" "));
    }

    private Page<QuestionDto> fromPage(Page<Question> page) {
        Map<Integer, String> idsMap = questionTagService.mapIdsToNames();
        List<QuestionDto> collect = page.getContent().stream()
                .map(i -> toDto(i, idsMap))
                .collect(Collectors.toList());
        return Page.of(collect, page.getPageable(), page.getTotalSize());
    }
}
