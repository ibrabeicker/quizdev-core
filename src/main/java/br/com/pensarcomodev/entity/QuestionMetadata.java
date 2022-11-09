package br.com.pensarcomodev.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionMetadata {

    private List<Integer> tags = new ArrayList<>();
}
