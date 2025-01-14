package com.example.resume.pipeline.extractors;
import com.example.resume.config.ApplicationConfig;
import com.example.resume.core.Stage;
import com.example.resume.dto.SectionDto;
import com.example.resume.entity.SectionType;
import com.example.resume.mapper.SectionMapper;
import com.example.resume.service.SectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Slf4j
@Component
public class ResumeSectionExtractor implements Stage<Long, Long> {

    @Autowired
    private final SectionService sectionService;

    private final List <String> sectionsList = new ArrayList<>();
    public ResumeSectionExtractor(SectionService sectionService) {
        this.sectionService = sectionService;
        this.sectionsList.add(ApplicationConfig.getConfigProperty("SKILL_SECTION"));
        this.sectionsList.add(ApplicationConfig.getConfigProperty("EDUCATION_SECTION"));
        this.sectionsList.add(ApplicationConfig.getConfigProperty("SUMMARY_SECTION"));
        this.sectionsList.add(ApplicationConfig.getConfigProperty("EXPERIENCE_SECTION"));
        this.sectionsList.add(ApplicationConfig.getConfigProperty("CERTIFICATION_SECTION"));
    }



    @Override
    public Long process(Long resumeId) {
        List<SectionDto> sectionsDto = extractSection(resumeId, sectionsList); //sectionService.getSectionByResumeAndType(resumeId, Section.SectionType.EDUCATION>
        sectionService.addSectionToResume(resumeId, SectionMapper.INSTANCE.toEntityList(sectionsDto));

        return resumeId;
    }

    public List<SectionDto> extractSection(Long resumeId, List<String> sections) {
        log.info("Extracting sections: {}", sections);
        String resumeContent = sectionService.getResumeContent(resumeId);
        List<String> selectedSections = selectSections(resumeContent, sections);
        List<String> sortedSections = sortPhrasesByPosition(resumeContent, selectedSections);

        List<SectionDto> blocks = new ArrayList<>();
        List<int[]> ranges = new ArrayList<>();

        addPrecedingTextSection(resumeContent, sortedSections, blocks);
        extractSectionContent(resumeContent, sortedSections, blocks, ranges);

        log.info("Extracted sections size: {}, sections: {}", blocks.size(), blocks);
        return blocks;
    }

    private List<String> selectSections(String resumeContent, List<String> sections) {
        List<String> selectedSections = new ArrayList<>();

        for (String sectionSet : sections) {
            String[] subSections = sectionSet.split("\\|");
            boolean found = false;
            for (String subPhrase : subSections) {
                if (resumeContent.toLowerCase().contains(subPhrase.toLowerCase().trim())) {
                    selectedSections.add(subPhrase.trim());
                    found = true;
                    break;
                }
            }
            if (!found && subSections.length > 0) {
                selectedSections.add(subSections[0].trim());
            }
        }
        return selectedSections;
    }

    private void addPrecedingTextSection(String resumeContent, List<String> sortedSections, List<SectionDto> blocks) {
        if (!sortedSections.isEmpty()) {
            String firstSection = sortedSections.get(0);
            int firstIndex = resumeContent.toLowerCase().indexOf(firstSection.toLowerCase().trim());
            if (firstIndex > 0) {
                String precedingText = resumeContent.substring(0, firstIndex).trim();
                if (!precedingText.isEmpty()) {
                    log.info("...first section: personal info");
                    SectionDto section = new SectionDto();
                    section.setSectionContent(precedingText);
                    section.setSectionType(SectionType.HEAD);
                    blocks.add(section);
                }
            }
        }
    }

    private void extractSectionContent(String resumeContent, List<String> sortedSections, List<SectionDto> blocks, List<int[]> ranges) {
        for (int i = 0; i < sortedSections.size(); i++) {
            String startOfSection = sortedSections.get(i);
            int startIndex = resumeContent.toLowerCase().indexOf(startOfSection.toLowerCase().trim());

            String endOfSection = (i + 1 < sortedSections.size()) ? sortedSections.get(i + 1) : null;
            int endIndex = (endOfSection != null) ? resumeContent.toLowerCase().indexOf(endOfSection.toLowerCase().trim()) : resumeContent.length();

            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                String sectionContent = resumeContent.substring(startIndex + startOfSection.length(), endIndex).trim();
                if (!sectionContent.isEmpty()) {
                    log.info("...section: start: {}", startOfSection);
                    SectionDto section = new SectionDto();
                    section.setSectionType(SectionType.identifySectionType(startOfSection));
                    section.setSectionContent(sectionContent);
                    blocks.add(section);
                    ranges.add(new int[]{startIndex, endIndex});
                }
            }
        }
    }

    private void addRemainingTextSection(String resumeContent, List<String> sortedSections, List<SectionDto> blocks) {
        if (!sortedSections.isEmpty()) {
            String lastSection = sortedSections.get(sortedSections.size() - 1);
            int lastIndex = resumeContent.toLowerCase().indexOf(lastSection.toLowerCase().trim()) + lastSection.length();
            if (lastIndex < resumeContent.length()) {
                String remainingText = resumeContent.substring(lastIndex).trim();
                if (!remainingText.isEmpty()) {
                    log.info("...last section: {}", lastSection);
                    SectionDto section = new SectionDto();
                    section.setSectionContent(remainingText);
                    section.setSectionType(SectionType.identifySectionType(lastSection));
                    //section.setSectionType(SectionDto.SectionType.LAST);
                    blocks.add(section);
                }
            }
        }
    }

    private List<String> sortPhrasesByPosition(String resumeContent, List<String> selectedSections) {
        selectedSections.sort((section1, section2) -> {
            int position1 = resumeContent.toLowerCase().indexOf(section1.toLowerCase());
            int position2 = resumeContent.toLowerCase().indexOf(section2.toLowerCase());
            return Integer.compare(position1, position2);
        });
        return selectedSections;
    }
}
