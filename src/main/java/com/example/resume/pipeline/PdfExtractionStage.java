package com.example.resume.pipeline;
import com.example.resume.core.Stage;
import com.example.resume.entity.Resume;
import com.example.resume.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import static com.example.resume.config.Constants.SAVE_RESUME_TO;

@Slf4j
@Component
public class PdfExtractionStage implements Stage<File, Long> {
    private static final Path CURRENT_PATH = Paths.get(System.getProperty("user.dir"));
    @Autowired
    private ResumeService resumeService;
    @Override
    public Long process(File input) {
              return  processPdfFile(input);
    }

    public Long processPdfFile(File pdfFile) {
        String uuid = UUID.randomUUID().toString();
        PDDocument document = null;
        try {
            document = PDDocument.load(pdfFile);
            log.info("Processing: Loading PDF file {}", pdfFile.getName());
            String resumeContent = extractTextFromPdf(document);
            log.info("Processing: Extracted text from PDF file {}", pdfFile.getName());
            String baseFileName = getBaseFileName(pdfFile.getName());
            File outDirectory = new File(CURRENT_PATH + "/" + SAVE_RESUME_TO);

            if (!outDirectory.exists()) {
                outDirectory.mkdirs();
            }
            File txtResumeFile = new File(outDirectory, baseFileName + "_" + uuid + ".txt");
            saveTextToFile(resumeContent, txtResumeFile);
            byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());
            byte[] txtBytes = Files.readAllBytes(txtResumeFile.toPath());
            Resume resume = new Resume();
            resume.setPdfFormat(pdfBytes);
            resume.setTxtFormat(txtBytes);
            resumeService.save(resume);
            return   resume.getId();

        } catch (IOException e) {
            log.error("Error processing PDF file {}: {}", pdfFile.getName(), e.getMessage());
            throw new RuntimeException("Failed to process PDF file: " + pdfFile.getName(), e);
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    log.error("Error closing PDF document: {}", e.getMessage());
                }
            }
            try {
                movePdfFile(pdfFile, new File(CURRENT_PATH + "/" + SAVE_RESUME_TO, getBaseFileName(pdfFile.getName()) + "_" + uuid + ".pdf"));
            } catch (IOException e) {
                log.error("Error moving PDF file {}: {}", pdfFile.getName(), e.getMessage());
                throw new RuntimeException("Failed to move PDF file: " + pdfFile.getName(), e);
            }
        }
    }


    private String extractTextFromPdf(PDDocument document) throws IOException {
        PDFTextStripper stripper = new PDFTextStripper();
        log.info("Extracting text from PDF document...");
        return stripper.getText(document);
    }

    private void saveTextToFile(String textContent, File file) throws IOException {
        log.info("Saving extracted text to file {}", file.getName());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(textContent);
        }
    }

    private void movePdfFile(File originalFile, File newFile) throws IOException {
        log.info("Moving PDF file to {}", newFile.getPath());
        Files.move(originalFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private String getBaseFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }
}
