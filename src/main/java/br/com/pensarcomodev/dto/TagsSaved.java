package br.com.pensarcomodev.dto;

import br.com.pensarcomodev.entity.QuestionTag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TagsSaved {

    private List<QuestionTag> existingTags;

    private List<QuestionTag> newTags;

    private List<QuestionTag> allTags = new ArrayList<>();

    public TagsSaved(List<QuestionTag> newTags, List<QuestionTag> existingTags) {
        this.existingTags = existingTags;
        this.newTags = newTags;
        allTags.addAll(newTags);
        allTags.addAll(existingTags);
    }

}
