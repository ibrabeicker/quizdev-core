package br.com.pensarcomodev.controller;

import br.com.pensarcomodev.dto.ChoiceQuestionDto;
import br.com.pensarcomodev.dto.CourseQuestionViewDto;
import br.com.pensarcomodev.service.CourseQuestionService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;

@Slf4j
@Controller("/course-question")
@RequiredArgsConstructor
public class CourseQuestionController {

    private final CourseQuestionService courseQuestionService;

    @Get("/{id}")
    public CourseQuestionViewDto getQuestionView(String id) {
        return courseQuestionService.getQuestionView(id);
    }

    @Post
    public ChoiceQuestionDto submitChoiceQuestion(@Body ChoiceQuestionDto dto, Principal user) {
        if (user != null && user.getName() != null) {
            dto.setStudentId(user.getName());
        }
        return courseQuestionService.submitChoiceQuestion(dto, user);
    }
}
