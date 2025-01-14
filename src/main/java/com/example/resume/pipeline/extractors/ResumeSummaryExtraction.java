package com.example.resume.pipeline.extractors;
import com.example.resume.core.Stage;
import com.example.resume.entity.SectionType;
import com.example.resume.grpc.ResumeProcessorClient;
import com.example.resume.service.SectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;


@Slf4j
@Component
public class ResumeSummaryExtraction implements Stage<Long, Long> {

    private final SectionService sectionService;

    public ResumeSummaryExtraction(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @Override
    public Long process(Long input) {
        try {

            SectionType type = SectionType.SUM;
            String summaryText = sectionService.getSectionByResumeAndType(input, type);
            type = SectionType.EXP;
            String experienceText = sectionService.getSectionByResumeAndType(input, type);
            Map<String, String> sections = new HashMap<>();
            sections.put("summary", summaryText);
            sections.put("experience", experienceText);
            List<String> skillWords = Arrays.asList("Java", "SQL", "Angular", "Spring Boot");

            Map<String, List<String>> networkDiagramData = findMatchingWords(sections, skillWords);

            List<String> labelsA = Arrays.asList("Summary Node 1", "Summary Node 2");
            List<Integer> valuesA = Arrays.asList(3, 5);

            List<String> labelsB = Arrays.asList("Experience Node 1", "Experience Node 2");
            List<Integer> valuesB = Arrays.asList(4, 2);

            List<String> labelsC = Arrays.asList("Other Node 1", "Other Node 2");
            List<Integer> valuesC = Arrays.asList(6, 1);

            String title = "Network Diagram";
            String xLabel = "Sections";
            String yLabel = "Word Matches";
            Integer xTicksRotation = 45;
            String colorA = "blue";
            String colorB = "green";
            String colorC = "red";
            Boolean showValues = true;

            ResumeProcessorClient.createNetworkDiagram(
                    labelsA, valuesA,
                    labelsB, valuesB,
                    labelsC, valuesC,
                    title, xLabel, yLabel,
                    xTicksRotation, colorA, colorB, colorC,
                    showValues, "src/main/resources/static/images/network_diagram.png",
                    "localhost");
            return input;
        } catch (
                Exception e) {
            log.error("Error: {}", e.getMessage());
            return null;
        }

    }

    public static Map<String, List<String>> findMatchingWords(Map<String, String> sections, List<String> skillWords) {
        Map<String, List<String>> networkDiagramData = new HashMap<>();

        for (Map.Entry<String, String> section : sections.entrySet()) {
            String sectionName = section.getKey();
            String sectionContent = section.getValue();

            List<String> matchedWords = new ArrayList<>();
            for (String word : skillWords) {
                if (sectionContent.toLowerCase().contains(word.toLowerCase())) {
                    matchedWords.add(word);
                }
            }

            if (!matchedWords.isEmpty()) {
                networkDiagramData.put(sectionName, matchedWords);
            }
        }

        return networkDiagramData;
    }


}
