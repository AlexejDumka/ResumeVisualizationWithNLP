package com.example.resume.service;

import com.example.resume.dto.SectionDto;
import com.example.resume.dto.SkillDto;
import com.example.resume.entity.Resume;
import com.example.resume.entity.Section;
import com.example.resume.entity.SectionType;
import com.example.resume.entity.Skill;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SectionService {
 @PersistenceContext
 private  EntityManager entityManager;


    @Transactional
    public Section save(Section section) {
        entityManager.persist(section);
        entityManager.flush();
        return section;
    }
    @Transactional(readOnly = true)
    public Section findById(Long id) throws  RuntimeException {
        return Optional.ofNullable(entityManager.find(Section.class, id)).orElseThrow();
    }
    @Transactional
    public Section update(Section section) {
        entityManager.detach(section);
        entityManager.merge(section);
        return section;
    }
    @Transactional
    public void delete(Long id) {
        Optional <Section> section  = Optional.of(Optional.ofNullable(entityManager.find(Section.class, id)).orElseThrow());
        {
            entityManager.remove(section);
        }

    }
    @Transactional
    public void addSectionToResume(Long resumeId, List<Section> sections) {
        Resume resume = entityManager.find(Resume.class, resumeId);
        if (resume == null) {
            throw new IllegalArgumentException("Resume not found with id: " + resumeId);
        }

        for (Section section : sections) {
            section.setResume(resume);
            entityManager.persist(section);
        }
    }

    public List<SectionDto> getSectionContent(List<SectionDto> sections, SectionType type) {
        return sections.stream()
                .filter(section -> section.getSectionType() == type)
                .collect(Collectors.toList());
    }
    public String getSectionByResumeAndType(Long resumeId, SectionType type) {
        TypedQuery<Section> sectionQuery = entityManager.createQuery(
                "SELECT s FROM Section AS s WHERE s.resume.id = :resumeId AND s.sectionType = :sectionType", Section.class);
        sectionQuery.setParameter("resumeId", resumeId);
        sectionQuery.setParameter("sectionType", type);
        return sectionQuery.getSingleResult().getSectionContent();
    }

    public String getResumeContent(Long resumeId) {
        Resume resume = entityManager.find(Resume.class, resumeId);
        if (resume == null) {
            throw new IllegalArgumentException("Resume not found with id: " + resumeId);
        }
        return new String(resume.getTxtFormat(), StandardCharsets.UTF_8);
    }
}
