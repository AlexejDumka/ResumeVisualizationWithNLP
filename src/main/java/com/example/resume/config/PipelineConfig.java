package com.example.resume.config;

import com.example.resume.core.Pipeline;
import com.example.resume.pipeline.extractors.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

@Configuration
public class PipelineConfig {

    @Bean
    public Pipeline<File, Long> ResumePdfContentExtractorPipeline(ResumePdfContentExtractor resumePdfContentExtractor) {
        return new Pipeline<>(List.of(resumePdfContentExtractor));
    }

    @Bean
    @Qualifier("education")
    public Pipeline<Long, Long> ResumeEducationsExtractionPipeline(ResumeEducationsExtraction resumeEducationsExtraction) {
        return new Pipeline<>(List.of(resumeEducationsExtraction));
    }

    @Bean
    @Qualifier("experience")
    public Pipeline<Long, Long> ResumeWorkExperiencesExtractionPipeline(ResumeExperiencesExtraction resumeExperiencesExtraction) {
        return new Pipeline<>(List.of(resumeExperiencesExtraction));
    }

    @Bean
    @Qualifier("skill")
    public Pipeline<Long, Long> ResumeSkiilsExtractionPipeline(ResumeSkillsExtractor resumeSkillsExtractor) {
        return new Pipeline<>(List.of(resumeSkillsExtractor));
    }

    @Bean
    @Qualifier("section")
    public Pipeline<Long, Long> ResumeSectionExtractionPipeline(ResumeSectionExtractor resumeSectionExtraction) {
        return new Pipeline<>(List.of(resumeSectionExtraction));
    }


    @Bean
    @Qualifier("personalInfo")
    public Pipeline<Long, Long> ResumePersonalInfoExtractionPipeline(ResumePersonalInfoExtractor resumePersonalInfoExtractor) {
        return new Pipeline<>(List.of(resumePersonalInfoExtractor));
    }
    @Bean
    @Qualifier("summary")
    public Pipeline<Long, Long> ResumeSummaryExtractionPipeline(ResumeSummaryExtraction resumeSummaryExtraction) {
        return new Pipeline<>(List.of(resumeSummaryExtraction));
    }
}