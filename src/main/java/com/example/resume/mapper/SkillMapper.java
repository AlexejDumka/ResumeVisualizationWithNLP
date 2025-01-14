package com.example.resume.mapper;
import com.example.resume.dto.SkillDto;
import com.example.resume.entity.Skill;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkillMapper {
    SkillMapper INSTANCE = Mappers.getMapper(SkillMapper.class);
    Skill toEntity(SkillDto skillDto);
    SkillDto toDto(Skill skill);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Skill partialUpdate(SkillDto skillDto, @MappingTarget Skill skill);
    List<SkillDto> entityListToDtoList(List<Skill> skills);
    List<Skill> dtoListToEntityList(List<SkillDto> skillsDto);
}
