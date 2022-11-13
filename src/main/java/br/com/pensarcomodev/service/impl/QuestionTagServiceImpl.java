package br.com.pensarcomodev.service.impl;

import br.com.pensarcomodev.dto.TagsSaved;
import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.entity.QuestionTag;
import br.com.pensarcomodev.entity.QuestionTagRelation;
import br.com.pensarcomodev.repository.QuestionTagRelationRepository;
import br.com.pensarcomodev.repository.QuestionTagRepository;
import br.com.pensarcomodev.service.QuestionTagService;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static br.com.pensarcomodev.JavaUtils.map;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class QuestionTagServiceImpl implements QuestionTagService {

    private final QuestionTagRepository questionTagRepository;
    private final QuestionTagRelationRepository questionTagRelationRepository;

    @Override
    public void setTags(Question question) {
        List<String> tagsText = map(question.getTags(), QuestionTag::getName);
        TagsSaved savedTags = findSavedTags(tagsText);
        questionTagRepository.saveAll(savedTags.getNewTags());

        List<QuestionTag> currentQuestionTags = questionTagRelationRepository.findTagByQuestionEquals(question);
        if (currentQuestionTags.isEmpty()) {
            addTagRelation(question, savedTags.getAllTags());
        } else {
            List<QuestionTag> removedTags = getRemovedTags(savedTags.getAllTags(), currentQuestionTags);
            removeTagRelation(question, removedTags);
            List<QuestionTag> addedTags = getAddedTags(savedTags.getAllTags(), currentQuestionTags);
            addTagRelation(question, addedTags);
        }
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
        return new TagsSaved(tagsToSave, savedTags);
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
    public List<QuestionTag> findTagByQuestionId(Long questionId) {
        return questionTagRelationRepository.findTagByQuestionEquals(Question.builder().id(questionId).build());
    }

    private List<QuestionTag> getRemovedTags(List<QuestionTag> newTags, List<QuestionTag> oldTags) {
        Set<QuestionTag> newTagsSet = new HashSet<>(newTags);
        return oldTags.stream()
                .filter(i -> !newTagsSet.contains(i))
                .collect(Collectors.toList());
    }

    private List<QuestionTag> getAddedTags(List<QuestionTag> newTags, List<QuestionTag> oldTags) {
        Set<QuestionTag> oldTagsSet = new HashSet<>(oldTags);
        return newTags.stream()
                .filter(i -> !oldTagsSet.contains(i))
                .collect(Collectors.toList());
    }

    private void removeTagRelation(Question question, List<QuestionTag> tags) {
        if (tags.isEmpty()) {
            return;
        }
        questionTagRelationRepository.deleteByQuestionEqualsAndTagInList(question, tags);
    }

    private void addTagRelation(Question question, List<QuestionTag> addedTags) {
        if (addedTags.isEmpty()) {
            return;
        }
        List<QuestionTagRelation> tags = addedTags.stream()
                .map(i -> QuestionTagRelation.builder().tag(i).question(question).build())
                .collect(Collectors.toList());
        questionTagRelationRepository.saveAll(tags);
    }
}
