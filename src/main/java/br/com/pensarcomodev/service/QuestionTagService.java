package br.com.pensarcomodev.service;

import br.com.pensarcomodev.entity.QuestionTag;

import java.util.List;
import java.util.Map;

public interface QuestionTagService {

    Map<Integer, String> mapIdsToNames();

    List<QuestionTag> findByIds(List<Integer> ids);

    List<Integer> findIdsByNames(List<String> tags);

    List<String> findNamesByIds(List<Integer> ids);
}
