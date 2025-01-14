package com.example.resume.pipeline;
import com.example.resume.core.Stage;
import com.example.resume.entity.Resume;
import com.example.resume.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.resume.config.Constants.STOPWORDS_PATH;

@Slf4j
@Component
public class TextNormalizationStage implements Stage<Long, Long> {
@Autowired
    private ResumeService resumeService;
    @Override
    public Long process(Long resumeId) {
        try {
           Resume resume = getResumeById(resumeId);
           String normalizedContent =  String.join(", ", normalizeText(getContent(resumeId)));
            resume.setNormalizedFormat(normalizedContent.getBytes(StandardCharsets.UTF_8));
         return resumeService.update(resume).getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Resume getResumeById(Long resumeId) {
       return Optional.ofNullable(resumeService.findById(resumeId)).orElseThrow(RuntimeException::new);
    }
    public  String getContent(Long resumeId) {
        Resume resume = getResumeById(resumeId);
        return new String(resume.getTxtFormat(), StandardCharsets.UTF_8);
    }
    public static List<String> normalizeText(String content) throws IOException {
        log.info("Starting text normalization.");
        List<String> words = List.of(content.split("\\s+"));
        words = removeNonAscii(words);
        log.info("Removed non-ASCII characters.");
        words = toLowerCase(words);
        log.info("Converted to lower case.");
        words = removePunctuation(words);
        log.info("Removed punctuation.");
        words = removeStopWords(words);
        log.info("Removed stop words.");
        log.info("Text normalization completed.");
        return words;
    }

    private static List<String> removeNonAscii(List<String> words) {
        return words.stream()
                .map(word -> Normalizer.normalize(word, Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", ""))
                .collect(Collectors.toList());
    }

    private static List<String> toLowerCase(List<String> words) {
        return words.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    private static List<String> removePunctuation(List<String> words) {
        return words.stream()
                .map(word -> word.replaceAll("[^\\w\\s]", ""))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toList());
    }

    private static List<String> loadStopWords() throws IOException {
        try (InputStream stopWordsIn = new FileInputStream(STOPWORDS_PATH)) {
            return IOUtils.readLines(stopWordsIn, StandardCharsets.UTF_8);
        }
    }

    private static List<String> removeStopWords(List<String> words) throws IOException {
        List<String> stopWords = loadStopWords();
        return words.stream()
                .filter(word -> !stopWords.contains(word))
                .collect(Collectors.toList());
    }


}
