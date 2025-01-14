package com.example.resume.pipeline.extractors;
import com.example.resume.core.Stage;
import com.example.resume.entity.SectionType;
import com.example.resume.grpc.GRPC;
import com.example.resume.grpc.ResumeProcessorClient;
import com.example.resume.service.SectionService;
import com.example.resume.service.SkillService;
import com.example.resume.util.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class ResumeSkillsExtractor implements Stage<Long, Long> {

    private final SkillService skillService;

    private final SectionService sectionService;

    public ResumeSkillsExtractor(SkillService skillService, SectionService sectionService) {
        this.skillService = skillService;
        this.sectionService = sectionService;
    }

    @Override
    public Long process(Long input) {
        try {
            SectionType type = SectionType.SKILL;
            String skills = sectionService.getSectionByResumeAndType(input, type);
            List<String> skillsList = List.of(skills.trim().split("\\s+"));
            skillsList = TextUtils.removePunctuation(skillsList);
            skillsList = TextUtils.removeNonAscii(skillsList);
            skillsList = TextUtils.removeStopWords(skillsList);
            String[] tokens = TextUtils.tokenizeText(String.join(" ", skillsList));
            log.info("tokens: " + String.join(" ", skillsList));
            skillService.saveSkillsFromListString(skillsList, input);

            ResumeProcessorClient.createWordCloud(String.join(" ", skillsList), "src/main/resources/static/images/word_cloud.png", 1200, 800, 14, "white", "viridis", 12, "localhost");
            Map<String, Double> categoryPercentages = skillService.compareSkills(input);
            List<String> labels = new ArrayList<>(categoryPercentages.keySet());
            List<Integer> values = categoryPercentages.values().stream().map(Double::intValue).toList();
            ResumeProcessorClient.createGraph(labels,values,"","xlabel", "ylabel", 45,"red","green","blue", true,"Test Bar Graph", GRPC.GraphType.BAR, null, "src/main/resources/static/images/percentage_skills.png", 800, 600, "black", "viridis", true, false, false, "top", 16, 12, 12, "-", "o", 1.0f, "gray", "--", 0.5f, "%.2f");
        } catch (IOException ex) {
            log.error("Error extracting skills: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }

        return input;


    }
}