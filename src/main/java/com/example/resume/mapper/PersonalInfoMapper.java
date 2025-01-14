package com.example.resume.mapper;
import com.example.resume.dto.PersonalInfoDto;
import com.example.resume.entity.Resume;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonalInfoMapper {
    PersonalInfoMapper INSTANCE = Mappers.getMapper(PersonalInfoMapper.class);
    Resume toEntity(PersonalInfoDto personalInfoDto);

    PersonalInfoDto toDto(Resume resume);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Resume partialUpdate(PersonalInfoDto sectionDto, @MappingTarget Resume resume);
}