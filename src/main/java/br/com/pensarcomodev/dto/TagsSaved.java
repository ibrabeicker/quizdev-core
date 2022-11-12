package br.com.pensarcomodev.dto;

import br.com.pensarcomodev.entity.QuestionTag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TagsSaved {

    private List<QuestionTag> existingTags = new ArrayList<>();

    private List<QuestionTag> newTags = new ArrayList<>();
}
