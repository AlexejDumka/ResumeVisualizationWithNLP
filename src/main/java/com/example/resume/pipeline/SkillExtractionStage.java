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
            return List.of(input.split("\\s+"));
        } catch (Exception e) {
            log.error("Error extracting skills: {}", e.getMessage());
        }
        return null;
    }

}
