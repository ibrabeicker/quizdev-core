package br.com.pensarcomodev.service;

import br.com.pensarcomodev.dto.TagsSaved;
import br.com.pensarcomodev.entity.QuestionTag;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface QuestionTagService {

    Map<Integer, String> mapIdsToNames();

    List<QuestionTag> findByIds(List<Integer> ids);

    List<Integer> findIdsByNames(List<String> tags);

    List<String> findNamesByIds(List<Integer> ids);

    TagsSaved findSavedTags(List<String> tagsText);

    List<QuestionTag> createTags(List<QuestionTag> newTags);

    List<QuestionTag> fromNames(List<String> tagsText);

    void createNewTags(Set<QuestionTag> tags);
}
