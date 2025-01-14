package com.example.resume.pipeline;
import com.example.resume.core.Stage;
import com.example.resume.entity.SectionType;
import com.example.resume.service.SectionService;
import com.example.resume.service.SkillService;
import opennlp.tools.tokenize.SimpleTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SkillsInExperienceStage implements Stage<Long, Map<String, Integer>> {
    private static SkillService skillService = null;
    private static SectionService sectionService = null;
    @Autowired
    public SkillsInExperienceStage(SkillService skillService, SectionService sectionService) {
        SkillsInExperienceStage.skillService = skillService;
        SkillsInExperienceStage.sectionService = sectionService;
    }

    @Override
    public Map<String, Integer> process(Long resumeId) {
        return findSkillsInExperience(resumeId);
    }
    public static Map<String, Integer> findSkillsInExperience(Long resumeId) {
        List<String> skills = skillService.getSkillListByResume(resumeId);
        SectionType type = SectionType.EXP;
        String experienceText = Optional.ofNullable(sectionService.getSectionByResumeAndType(resumeId, type)).orElseThrow();

        Map<String, Integer> matchingSkills = Stream.of(experienceText.toLowerCase().split("\\W+"))
                .filter(skill -> skills.stream().map(String::toLowerCase).toList().contains(skill))
                .collect(Collectors.toMap(
                        skill -> skill,
                        skill -> 1,
                        Integer::sum
                ));

        Set<String> matchingPhrases = findMatchingPhrases(experienceText, String.join(" ", skills));

        Map<String, Integer> matchingPhrasesMap = matchingPhrases.stream()
                .collect(Collectors.toMap(
                        phrase -> phrase,
                        phrase -> Collections.frequency(matchingSkills.keySet(), phrase)
                ));

        matchingSkills.putAll(matchingPhrasesMap);
        return matchingSkills;
    }
    private static Set<String> findMatchingPhrases(String experienceText, String skillsText) {
        List<String> experienceBigrams = getBigrams(experienceText);
        List<String> experienceTrigrams = getTrigrams(experienceText);
        List<String> skillBigrams = getBigrams(skillsText);
        List<String> skillTrigrams = getTrigrams(skillsText);
        Set<String> matchingPhrases = new HashSet<>();
        for (String bigram : experienceBigrams) {
            if (skillBigrams.contains(bigram)) {
                matchingPhrases.add(bigram);
            }
        }
        for (String trigram : experienceTrigrams) {
            if (skillTrigrams.contains(trigram)) {
                matchingPhrases.add(trigram);
            }
        }

        return matchingPhrases;
    }

    private static List<String> getBigrams(String text) {
        String[] tokens = tokenizeText(text);
        List<String> bigrams = new ArrayList<>();

        for (int i = 0; i < tokens.length - 1; i++) {
            bigrams.add(tokens[i] + " " + tokens[i + 1]);
        }
        return bigrams;
    }

    private static List<String> getTrigrams(String text) {
        String[] tokens = tokenizeText(text);
        List<String> trigrams = new ArrayList<>();

        for (int i = 0; i < tokens.length - 2; i++) {
            trigrams.add(tokens[i] + " " + tokens[i + 1] + " " + tokens[i + 2]);
        }
        return trigrams;
    }
    private static String[] tokenizeText(String text) {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        return tokenizer.tokenize(text);
    }
}
