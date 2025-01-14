package com.example.resume.util;

import com.example.resume.dto.SkillDto;
import com.example.resume.entity.Skill;
import com.example.resume.mapper.SkillMapper;
import com.example.resume.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SkillExtractor {

    @Autowired
    private SkillService skillService;

    public List<String> extractSkills(List<String> tokens, List<String> nounChunks) {
        List<SkillDto> skillsDto = skillService.getAllSkillsDto();
        List<String> skills = skillService.convertSkillsToStringList(SkillMapper.INSTANCE.dtoListToEntityList(skillsDto));
        Set<String> skillset = new HashSet<>();

        // check for one-grams
        for (String token : tokens) {
            if (skills.contains(token.toLowerCase())) {
                skillset.add(token);
            }
        }

        // check for bi-grams and tri-grams
        for (String token : nounChunks) {
            if (skills.contains(token.toLowerCase().strip())) {
                skillset.add(token);
            }
        }

        return skillset.stream().map(String::toLowerCase).map(String::toUpperCase).collect(Collectors.toList());
    }
}
