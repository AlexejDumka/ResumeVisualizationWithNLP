package com.example.resume.mapper;
import com.example.resume.dto.SectionDto;
import com.example.resume.entity.Section;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SectionMapper {
    SectionMapper INSTANCE = Mappers.getMapper(SectionMapper.class);
    Section toEntity(SectionDto sectionDto);
    List<Section> toEntityList(List<SectionDto> sectionDtos);
    SectionDto toDto(Section section);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Section partialUpdate(SectionDto sectionDto, @MappingTarget Section section);
}