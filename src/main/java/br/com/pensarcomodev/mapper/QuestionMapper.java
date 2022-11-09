package br.com.pensarcomodev.mapper;

import br.com.pensarcomodev.dto.QuestionDto;
import br.com.pensarcomodev.entity.Question;
import org.mapstruct.Mapper;

@Mapper
public interface QuestionMapper {

    Question fromDto(QuestionDto dto);

    QuestionDto toDto(Question question);

}
