package com.example.resume.service;

import com.example.resume.entity.Resume;
import com.example.resume.entity.Section;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {

        private final EntityManager entityManager;

    @Autowired
    public ResumeService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Resume save(Resume resume) {
        entityManager.persist(resume);
        entityManager.flush();
        return resume;
    }

    @Transactional(readOnly = true)
    public Resume findById(Long id) throws RuntimeException {
        return Optional.ofNullable(entityManager.find(Resume.class, id)).orElseThrow();
    }

    @Transactional
    public void createResume(String name,
                             String phone,
                             String address,
                             String linkedinLink,
                             String githubLink,
                             byte[] pdfBytes,
                             byte[] txtBytes,
                             byte[] txtNormalizedBytes,
                             List<Section> sections){
        Resume resume = new Resume();
        resume.setName(name);
        resume.setPhone(phone);
        resume.setAddress(address);
        resume.setLinkedinLink(linkedinLink);
        resume.setGithubLink(githubLink);
        resume.setPdfFormat(pdfBytes);
        resume.setTxtFormat(txtBytes);
        resume.setTxtFormat(txtNormalizedBytes);
        for(Section section : sections){
            section.setResume(resume);
            resume.getSections().add(section);
        }

        entityManager.merge(resume);
    }

    @Transactional
    public void delete(Long id) {
        Resume resume = Optional.ofNullable(entityManager.find(Resume.class, id)).orElseThrow();
        entityManager.remove(resume);
    }

    @Transactional
    public Resume saveResumeFile(MultipartFile pdfFile, MultipartFile txtFile) throws IOException {
        Resume resume = new Resume();
        if (pdfFile != null && !pdfFile.isEmpty()) {
            resume.setPdfFormat(pdfFile.getBytes());
        }
        if (txtFile != null && !txtFile.isEmpty()) {
            resume.setTxtFormat(txtFile.getBytes());
        }
        return save(resume);
    }

    public Resume setResumeFiles(byte[] pdfBytes, byte[] txtBytes) {
        Resume resume = new Resume();
        resume.setPdfFormat(pdfBytes);
        resume.setTxtFormat(txtBytes);
        return resume;
    }
    public String getTxtContent(Long id) throws Exception {
        Optional<Resume> resume = Optional.of(Optional.ofNullable(entityManager.find(Resume.class, id)).orElseThrow());
        byte[] txtFormatBytes = resume.get().getTxtFormat();
        if (txtFormatBytes != null) {
            return new String(txtFormatBytes, StandardCharsets.UTF_8);
        } else {
            throw new Exception("Txt format is empty");
        }
    }
    @Transactional
    public Section addSectionToResume(Long resumeId, Section section) {
        Optional<Resume> resume = Optional.of(Optional.ofNullable(entityManager.find(Resume.class, resumeId)).orElseThrow());
        section.setResume(resume.get());
        entityManager.merge(section);
        resume.get().getSections().add(section);
        entityManager.merge(resume);
        return section;
    }
    @Transactional
    public Resume update(Resume resume) {
        entityManager.detach(resume);
        entityManager.merge(resume);
        return resume;
    }
}
