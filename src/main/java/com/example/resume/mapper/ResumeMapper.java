package com.example.resume.mapper;
import com.example.resume.dto.PersonalInfoDto;
import com.example.resume.dto.SectionDto;
import com.example.resume.entity.Resume;
import com.example.resume.dto.ResumeDto;
import com.example.resume.entity.Section;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    ResumeMapper INSTANCE = Mappers.getMapper(ResumeMapper.class);
    ResumeDto entityToDto(Resume entity);
    Resume dtoToEntity(ResumeDto dto);
    Resume toEntity(ResumeDto resumeDto);
    ResumeDto fromPersonalInfoDto(PersonalInfoDto personalInfoDto);
    Resume fromPersonalInfoDtoToResume(PersonalInfoDto personalInfoDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Resume partialUpdate(ResumeDto resumeDto, @MappingTarget Resume resume);
    List<Section> dtoToEntityList(List<SectionDto> sectionsDto);
    List<SectionDto> entityToDtoList(List<Section> sections);
}