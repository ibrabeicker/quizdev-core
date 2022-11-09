package br.com.pensarcomodev.service.impl;

import br.com.pensarcomodev.entity.QuestionTag;
import br.com.pensarcomodev.repository.QuestionTagRepository;
import br.com.pensarcomodev.service.QuestionTagService;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
}
