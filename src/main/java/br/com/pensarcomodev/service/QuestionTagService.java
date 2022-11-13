package br.com.pensarcomodev.service;

import br.com.pensarcomodev.dto.TagsSaved;
import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.entity.QuestionTag;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface QuestionTagService {


    List<QuestionTag> findByIds(List<Integer> ids);

    List<Integer> findIdsByNames(List<String> tags);

    List<String> findNamesByIds(List<Integer> ids);

    TagsSaved findSavedTags(List<String> tagsText);

    List<QuestionTag> fromNames(List<String> tagsText);

    List<QuestionTag> findTagByQuestionId(Long questionId);

    void setTags(Question question);
}
