package com.example.resume.core;

import com.example.resume.pipeline.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

@Configuration
public class PipelineConfig {

    @Bean
    public Pipeline<File, String> filePipeline(PdfExtractionStage pdfExtractionStage) {
        return new Pipeline<>(List.of(pdfExtractionStage));
    }

    @Bean
    public Pipeline<String, String> textPipeline(TextNormalizationStage textNormalizationStage) {
        return new Pipeline<>(List.of(textNormalizationStage));
    }

    @Bean
    public Pipeline<String, List<String>> sectionPipeline(SectionSplittingStage sectionSplittingStage) {
        return new Pipeline<>(List.of(sectionSplittingStage));
    }

    @Bean
    public Pipeline<String, List<String>> keywordPipeline(KeywordSearchStage keywordSearchStage) {
        return new Pipeline<>(List.of(keywordSearchStage));
    }

    @Bean
    public Pipeline<String, List<String>> skillPipeline(SkillSearchStage skillSearchStage) {
        return new Pipeline<>(List.of(skillSearchStage));
    }

    @Bean
    public Pipeline<String, String> categorizationPipeline(CategorizationStage categorizationStage) {
        return new Pipeline<>(List.of(categorizationStage));
    }
}
