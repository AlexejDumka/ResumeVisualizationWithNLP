package com.example.resume.service;

import com.example.resume.dto.SkillDto;
import com.example.resume.entity.Resume;
import com.example.resume.entity.Skill;
import com.example.resume.entity.SkillCategory;
import com.example.resume.mapper.SkillMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SkillService {
         @PersistenceContext
        private EntityManager entityManager;

             public Skill save(Skill skill)  {
            entityManager.persist(skill);
            entityManager.flush();
            return skill;
        }
        public Skill findById(Long id) throws  RuntimeException {
            return Optional.ofNullable(entityManager.find(Skill.class, id)).orElseThrow();
        }
@Transactional
        public Skill update(Skill skill) {
             entityManager.detach(skill);
            entityManager.merge(skill);
            return skill;
        }

        public void delete(Long id) {
            Optional <Skill> skill  = Optional.of(Optional.ofNullable(entityManager.find(Skill.class, id)).orElseThrow());
            {
                entityManager.remove(skill);
            }

        }
        public List<SkillDto> getAllSkillsDto() {
            return SkillMapper.INSTANCE.entityListToDtoList(entityManager.createQuery("select s from Skill s", Skill.class).getResultList());
        }

    public List<String> convertSkillsToStringList(List<Skill> skills) {
        return skills.stream().map(Skill::getSkillName).collect(Collectors.toList());
    }
    @Transactional
    public void saveSkillsFromListString(List<String> skills, Long resumeId) {
        Resume resume = Optional.ofNullable(entityManager.find(Resume.class, resumeId)).orElseThrow();

            for (String skill : skills) {
            if (!skill.isEmpty()) {
                Skill skillEntity = new Skill();
                skillEntity.setSkillName(skill.trim().toLowerCase());
                skillEntity.setResume(resume);
                entityManager.persist(skillEntity);

            }
        }

    }

@Transactional
    public void saveSkillsFromString(String skillString, Long resumeId) {
        Resume resume = entityManager.find(Resume.class, resumeId);
        if (resume == null) {
            throw new IllegalArgumentException("Resume not found with id: " + resumeId);
        }
        String[] skillArray = skillString.split(",\\s*");
        List<String> skills = Arrays.asList(skillArray);

        for (String skill : skills) {
            if (!skill.isEmpty()) {
                Skill skillEntity = new Skill();
                skillEntity.setSkillName(skill.trim().toLowerCase());
                skillEntity.setResume(resume);
                entityManager.persist(skillEntity);

            }
           // entityManager.flush();
        }

    }

    public void saveSkillsFromString(String skillString) {
        String[] skillArray = skillString.split(",\\s*");
        List<String> skills = Arrays.asList(skillArray);

        for (String skill : skills) {
            if (!skill.isEmpty()) {
                Skill skillEntity = new Skill();
                skillEntity.setSkillName(skill.trim().toLowerCase()); // Приводим все к нижнему регистру для консистентности
                entityManager.persist(skillEntity);
                entityManager.flush();
            }
        }
    }

    public List<String> getAllSkills() {
        List<Skill> skills = entityManager.createQuery("select s from Skill s", Skill.class).getResultList();
        return skills.stream().map(Skill::getSkillName).collect(Collectors.toList());
    }

    public Map<String, Double> compareSkills(Long resumeId) {
        // Получение навыков из резюме
        List<Skill> resumeSkills = getResumeSkills(resumeId);
        debugPrint("Resume Skills", resumeSkills);

        // Получение обязательных навыков
        List<SkillCategory> requiredSkills = getRequiredSkills();
        debugPrint("Required Skills", requiredSkills);

        // Группировка обязательных навыков по категориям
        Map<String, List<String>> requiredSkillsByCategory = groupRequiredSkillsByCategory(requiredSkills);
        debugPrint("Required Skills by Category", requiredSkillsByCategory);

        // Группировка навыков из резюме по категориям
        Map<String, List<String>> resumeSkillsByCategory = groupResumeSkillsByCategory(resumeSkills, requiredSkills);
        debugPrint("Resume Skills by Category", resumeSkillsByCategory);

        // Расчет процентов присутствия навыков по категориям
        Map<String, Double> categoryPercentages = calculateCategoryPercentages(requiredSkillsByCategory, resumeSkillsByCategory);
        debugPrint("Category Percentages", categoryPercentages);

        return categoryPercentages;
    }

    public List<Skill> getResumeSkills(Long resumeId) {
        TypedQuery<Skill> resumeSkillsQuery = entityManager.createQuery(
                "SELECT s FROM Skill AS s WHERE s.resume.id = :resumeId", Skill.class);
        resumeSkillsQuery.setParameter("resumeId", resumeId);
        return resumeSkillsQuery.getResultList();
    }

    public List<String> getSkillListByResume(Long resumeId) {
        TypedQuery <String> skillsQuery = entityManager.createQuery("SELECT sc.skillName FROM Skill sc where sc.resume.id = :resumeId", String.class);
        skillsQuery.setParameter("resumeId", resumeId);
        return skillsQuery.getResultList();
    }

    private List<SkillCategory> getRequiredSkills() {
        TypedQuery<SkillCategory> requiredSkillsQuery = entityManager.createQuery(
                "SELECT sc FROM SkillCategory AS sc", SkillCategory.class);
        return requiredSkillsQuery.getResultList();
    }

    private Map<String, List<String>> groupRequiredSkillsByCategory(List<SkillCategory> requiredSkills) {
        return requiredSkills.stream()
                .filter(sc -> sc.getSkillCategoryName() != null) // Отфильтровываем null значения
                .collect(Collectors.groupingBy(
                        sc -> sc.getSkillCategoryName().toLowerCase(),
                        Collectors.mapping(sc -> sc.getSkillName().toLowerCase(), Collectors.toList())
                ));
    }

    private Map<String, List<String>> groupResumeSkillsByCategory(List<Skill> resumeSkills, List<SkillCategory> requiredSkills) {
        Set<String> requiredSkillNames = requiredSkills.stream()
                .map(sc -> sc.getSkillName().toLowerCase())
                .collect(Collectors.toSet());

        return resumeSkills.stream()
                .map(skill -> skill.getSkillName().toLowerCase())
                .filter(requiredSkillNames::contains)
                .map(skill -> new AbstractMap.SimpleEntry<>(skill, getSkillCategoryName(skill, requiredSkills)))
                .filter(entry -> entry.getValue() != null) // Фильтруем null значения
                .collect(Collectors.groupingBy(
                        entry -> entry.getValue().toLowerCase(),
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                ));
    }

    private String getSkillCategoryName(String skillName, List<SkillCategory> requiredSkills) {
        return requiredSkills.stream()

                .filter(sc -> sc.getSkillName().equalsIgnoreCase(skillName))
                .map(SkillCategory::getSkillCategoryName)
                .filter(sc -> sc != null && !sc.isEmpty())
                .findFirst()
                .orElse("unknown");
    }

    private Map<String, Double> calculateCategoryPercentages(Map<String, List<String>> requiredSkillsByCategory, Map<String, List<String>> resumeSkillsByCategory) {
        Map<String, Double> categoryPercentages = new HashMap<>();

        for (String category : requiredSkillsByCategory.keySet()) {
            List<String> required = requiredSkillsByCategory.get(category);
            List<String> present = resumeSkillsByCategory.getOrDefault(category, Collections.emptyList());

            if (!present.isEmpty()) {
                long matchedCount = present.stream().filter(required::contains).count();
                double percentage = (double) matchedCount / required.size() * 100;
                categoryPercentages.put(category, percentage);
            }
        }

        return categoryPercentages;
    }

    private void debugPrint(String label, Object data) {
        System.out.println(label + ": " + data);
    }}
