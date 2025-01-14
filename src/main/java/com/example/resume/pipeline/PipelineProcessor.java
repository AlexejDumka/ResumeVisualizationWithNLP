package com.example.resume.pipeline;
import com.example.resume.core.Pipeline;
import com.example.resume.dto.SectionDto;
import com.example.resume.entity.SectionType;
import com.example.resume.service.ResumeService;
import com.example.resume.service.SectionService;
import com.example.resume.service.SkillService;
import io.grpc.ManagedChannel;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PipelineProcessor {
    private ManagedChannel channel;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private SkillService skillService;


    private final Pipeline<File, Long> resumePdfContentExtractorPipeline;
    private final Pipeline<Long, Long> resumeEducationsExtractionPipeline;
    private final Pipeline<Long, Long> resumeWorkExperiencesExtractionPipeline;
    private final Pipeline<Long, Long> resumeSkillsExtractionPipeline;
    private final Pipeline<Long, Long> resumeSummaryExtractionPipeline;
    private final Pipeline<Long, Long> resumePersonalInfoExtractionPipeline;

    private final Pipeline<Long, Long> resumeSectoionExtractionPipeline;

    private PipelineProcessor(
            Pipeline<File, Long> resumePdfContentExtractorPipeline,
            @Qualifier("education") Pipeline<Long, Long> resumeEducationsExtractionPipeline,
            @Qualifier("experience") Pipeline<Long, Long> resumeWorkExperiencesExtractionPipeline,
            @Qualifier("skill") Pipeline<Long, Long> resumeSkillsExtractionPipeline,
            @Qualifier("summary") Pipeline<Long, Long> resumeSummaryExtractionPipeline,
            @Qualifier("personalInfo") Pipeline<Long, Long> resumePersonalInfoExtractionPipeline,
            @Qualifier("section") Pipeline<Long, Long> resumeSectoionExtractionPipeline) {
        this.resumePdfContentExtractorPipeline = resumePdfContentExtractorPipeline;
        this.resumeEducationsExtractionPipeline = resumeEducationsExtractionPipeline;
        this.resumeWorkExperiencesExtractionPipeline = resumeWorkExperiencesExtractionPipeline;
        this.resumeSkillsExtractionPipeline = resumeSkillsExtractionPipeline;
        this.resumeSummaryExtractionPipeline = resumeSummaryExtractionPipeline;
        this.resumePersonalInfoExtractionPipeline = resumePersonalInfoExtractionPipeline;
        this.resumeSectoionExtractionPipeline = resumeSectoionExtractionPipeline;
    }

    public String process(File inputDirectory) {
        List<File> filesToProcess = getFilesFromDirectory(inputDirectory);
        log.info("Will be processing " + filesToProcess.size() + " files...");
        StringBuilder result = new StringBuilder();

        try {
            for (File pdfFile : filesToProcess) {
                Long resumeId = resumePdfContentExtractorPipeline.execute(pdfFile);
                Long resumeId3 = resumeSectoionExtractionPipeline.execute(resumeId);
                Long resumeId2 = resumeSkillsExtractionPipeline.execute(resumeId);
                Long resumeId4 = resumeSummaryExtractionPipeline.execute(resumeId);
            }
            return result.toString();
        } catch (Exception e) {
            log.error("An error occurred during processing", e);
            return null;
        }
    }
    private List<File> getFilesFromDirectory(File directory) {
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".pdf"));
        return files != null ? List.of(files) : new ArrayList<>();
    }
    public static List<SectionDto> extractSectionsByType(List<SectionDto> sectionInfos, SectionType typeToExtract) {
        List<SectionDto> result = new ArrayList<>();

        for (SectionDto sectionInfo : sectionInfos) {
            SectionType type = SectionType.identifySectionType(sectionInfo.toString());

            if (type == typeToExtract) {
                result.add(sectionInfo);
            }
        }

        return result;
    }
    @PreDestroy
    public void shutdown() {
        channel.shutdown();
        try {
            if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                channel.shutdownNow();
            }
        } catch (InterruptedException e) {
            channel.shutdownNow();
        }
    }
}
