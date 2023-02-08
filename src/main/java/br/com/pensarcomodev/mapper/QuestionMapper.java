package br.com.pensarcomodev.mapper;

import br.com.pensarcomodev.dto.ChoiceQuestionDto;
import br.com.pensarcomodev.dto.CourseQuestionViewDto;
import br.com.pensarcomodev.entity.SubmittedAnswer;
import br.com.pensarcomodev.dto.QuestionDto;
import br.com.pensarcomodev.entity.Question;
import br.com.pensarcomodev.entity.QuestionTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330")
public interface QuestionMapper {

    @Mapping(target = "tags", source = "tags", ignore = true)
    Question fromDto(QuestionDto dto);

//    @Mapping(target = "tags", source = "tags")
    QuestionDto toDto(Question question);

    SubmittedAnswer.Choice fromSubmittedAnswer(ChoiceQuestionDto.Answer answer);

    List<SubmittedAnswer.Choice> fromSubmittedAnswers(List<ChoiceQuestionDto.Answer> answers);

    CourseQuestionViewDto toViewDto(Question question);

//    @Named("tagsMapping")
    default List<String> map(List<QuestionTag> tags) {
        if (tags == null || tags.isEmpty()) {
            return Collections.emptyList();
        }
        return tags.stream().map(QuestionTag::getName).collect(Collectors.toList());
    }

}
