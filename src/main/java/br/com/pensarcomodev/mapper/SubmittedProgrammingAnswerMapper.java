package br.com.pensarcomodev.mapper;

import br.com.pensarcomodev.dto.ProgrammingQuestionSubmissionDto;
import br.com.pensarcomodev.entity.SubmittedProgrammingAnswer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface SubmittedProgrammingAnswerMapper {

    SubmittedProgrammingAnswer toEntity(ProgrammingQuestionSubmissionDto dto);
}
