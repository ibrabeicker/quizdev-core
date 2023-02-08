package br.com.pensarcomodev.service;

import br.com.pensarcomodev.dto.ChoiceQuestionDto;
import br.com.pensarcomodev.dto.CourseQuestionViewDto;

import java.security.Principal;

public interface CourseQuestionService {

    ChoiceQuestionDto submitChoiceQuestion(ChoiceQuestionDto dto, Principal user);

    CourseQuestionViewDto getQuestionView(String id);
}
