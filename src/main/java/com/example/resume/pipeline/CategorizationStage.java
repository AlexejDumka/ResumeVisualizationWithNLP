package com.example.resume.pipeline;
import com.example.resume.core.Stage;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Component
public class CategorizationStage implements Stage<String, String> {

    @Override
    public String process(String input) {

        try {
          return categorize(input);
        } catch (IOException e) {
            log.error("Error categorizing text: {}", e.getMessage());
        }
      return null;
    }
    public String categorize(String input) throws IOException {
        FileInputStream modelIn = new FileInputStream("src/main/resources/en-doccat.bin");
        DoccatModel model = new DoccatModel(modelIn);
        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
        String[] docWords = input.split("\\s+");
        double[] outcomes = categorizer.categorize(docWords);
        String category = categorizer.getBestCategory(outcomes);
        log.info("Predicted Category: " + category);
        return category;
    }

}



