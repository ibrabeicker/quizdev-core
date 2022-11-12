package br.com.pensarcomodev.service.impl;

import br.com.pensarcomodev.dto.TagsSaved;
import br.com.pensarcomodev.entity.QuestionTag;
import br.com.pensarcomodev.repository.QuestionTagRepository;
import br.com.pensarcomodev.service.QuestionTagService;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class QuestionTagServiceImpl implements QuestionTagService {

    private final QuestionTagRepository questionTagRepository;

    @Override
    public Map<Integer, String> mapIdsToNames() {
        Map<Integer, String> map = new HashMap<>();
        questionTagRepository.findAll().forEach(i -> {
            map.put(i.getId(), i.getName());
        });
        return map;
    }

    @Override
    public List<QuestionTag> findByIds(List<Integer> ids) {
        return questionTagRepository.findByIdInList(ids);
    }

    @Override
    public List<Integer> findIdsByNames(List<String> tags) {
        return questionTagRepository.findByNameInList(tags).stream()
                .map(QuestionTag::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findNamesByIds(List<Integer> ids) {
        return questionTagRepository.findByIdInList(ids).stream()
                .map(QuestionTag::getName)
                .collect(Collectors.toList());
    }

    @Override
    public TagsSaved findSavedTags(List<String> tagsText) {
        List<QuestionTag> savedTags = questionTagRepository.findByNameInList(tagsText);
        Set<String> savedTagsNames = savedTags.stream().map(QuestionTag::getName).collect(Collectors.toSet());
        List<QuestionTag> tagsToSave = tagsText.stream()
                .filter(i -> !savedTagsNames.contains(i))
                .map(i -> QuestionTag.builder().name(i).build())
                .collect(Collectors.toList());
        TagsSaved tagsSaved = new TagsSaved();
        tagsSaved.setNewTags(tagsToSave);
        tagsSaved.setExistingTags(savedTags);
        return tagsSaved;
    }

    @Override
    public List<QuestionTag> createTags(List<QuestionTag> newTags) {
        List<QuestionTag> result = new ArrayList<>();
        questionTagRepository.saveAll(newTags).forEach(result::add);
        return result;
    }

    @Override
    public List<QuestionTag> fromNames(List<String> tagsText) {
        List<QuestionTag> savedTags = questionTagRepository.findByNameInList(tagsText);
        List<QuestionTag> ret = new ArrayList<>(savedTags);
        Set<String> savedTagsNames = savedTags.stream().map(QuestionTag::getName).collect(Collectors.toSet());
        List<QuestionTag> tagsToSave = tagsText.stream()
                .filter(i -> !savedTagsNames.contains(i))
                .map(i -> QuestionTag.builder().name(i).build())
                .collect(Collectors.toList());
        ret.addAll(tagsToSave);
        return ret;
    }

    @Override
    public void createNewTags(Set<QuestionTag> tags) {
        log.info("{}", questionTagRepository.findAll());
        tags.stream()
                .filter(i -> i.getId() == null)
                .forEach(i -> {
                    QuestionTag saved = questionTagRepository.save(i);
                    i.setId(saved.getId());
                });
    }
}
