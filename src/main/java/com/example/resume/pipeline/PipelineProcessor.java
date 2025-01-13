package com.example.resume.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@Service
public class PipelineProcessor {

    private final Pipeline<File, String> filePipeline;
    private final Pipeline<String, String> textPipeline;
    private final Pipeline<String, List<String>> sectionPipeline;
    private final Pipeline<String, List<String>> keywordPipeline;
    private final Pipeline<String, List<String>> skillPipeline;

    public PipelineProcessor(Pipeline<File, String> filePipeline,
                             Pipeline<String, String> textPipeline,
                             Pipeline<String, List<String>> sectionPipeline,
                             Pipeline<String, List<String>> keywordPipeline,
                             Pipeline<String, List<String>> skillPipeline) {
        this.filePipeline = filePipeline;
        this.textPipeline = textPipeline;
        this.sectionPipeline = sectionPipeline;
        this.keywordPipeline = keywordPipeline;
        this.skillPipeline = skillPipeline;
    }

    public String process(File inputDirectory) {
        List<File> filesToProcess = getFilesFromDirectory(inputDirectory);
        log.info("Will be processing " + filesToProcess.size() + " files...");
        StringBuilder result = new StringBuilder();

        try {
            for (File file : filesToProcess) {
                // Извлечение текста из файла
                String extractedText = filePipeline.execute(file);

                // Нормализация текста
                String normalizedText = textPipeline.execute(extractedText);

                // Разбиение на секции
                List<String> sections = sectionPipeline.execute(normalizedText);

                // Поиск ключевых слов
                List<String> keywords = keywordPipeline.execute(normalizedText);

                // Поиск навыков
                List<String> skills = skillPipeline.execute(normalizedText);

                // Собираем результаты
                result.append("File: ").append(file.getName()).append("\n")
                        .append("Category: ").append(categorizationPipeline.execute(normalizedText)).append("\n")
                        .append("Sections: ").append(sections).append("\n")
                        .append("Keywords: ").append(keywords).append("\n")
                        .append("Skills: ").append(skills).append("\n\n");
            }
            return result.toString();
        } catch (Exception e) {
            log.error("An error occurred during processing", e);
            return null;
        }
    }

    private List<File> getFilesFromDirectory(File directory) {
        return List.of(directory.listFiles());
    }
}
