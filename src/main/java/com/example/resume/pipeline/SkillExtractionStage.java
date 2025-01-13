package com.example.resume.pipeline;

import com.example.resume.core.Stage;
import com.example.resume.util.SkillExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SkillExtractionStage implements Stage<String, List<String>> {

    @Autowired
    private SkillExtractor skillExtractor;

    @Override
    public List<String> process(String input) {
        try {
            return extractSkills(input);
        } catch (Exception e) {
            log.error("Error extracting skills: {}", e.getMessage());
        }
        return null;
    }

    public List<String> extractSkills(String input) {
        // Пример получения токенов и nounChunks (нужно заменить на реальную обработку)
        List<String> tokens = List.of(input.split("\\s+")); // Заглушка, замените на реальную обработку
        List<String> nounChunks = List.of(); // Заглушка, замените на реальную обработку

        List<String> skills = skillExtractor.extractSkills(tokens, nounChunks);
        log.info("Extracted skills: {}", skills);
        return skills;
    }
}
