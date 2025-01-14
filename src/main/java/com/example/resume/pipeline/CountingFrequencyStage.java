package com.example.resume.pipeline;
import com.example.resume.core.Stage;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CountingFrequencyStage implements Stage<String, List<String>> {


    @Override
    public List<String> process(String input) {
        List<String> tokens = tokenizeText(input);
        List<String> posTags = tagPOS(tokens);
        Map<String, Integer> posFreq = countFrequency(posTags);
        List<String> posList = posFreq.entrySet().stream().map(e -> e.getKey() + " " + e.getValue()).collect(Collectors.toList());
        log.info("POS tags: {}", posList);
         return posList;

    }

    public Map<String, Integer> countFrequency(List<String> posTags) {
        Map<String, Integer> posFreq = new HashMap<>();
        for (String pos : posTags) {
            posFreq.put(pos, posFreq.getOrDefault(pos, 0) + 1);
        }
        return posFreq;
    }

    private List<String> tokenizeText(String text) {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        return Arrays.asList(tokenizer.tokenize(text));
    }

    private List<String> tagPOS(List<String> tokens) {
        List<String> posTags = new ArrayList<>();
        try (InputStream modelIn =  getClass().getResourceAsStream("/en-pos-maxent.bin")) {
            POSModel model = new POSModel(Objects.requireNonNull(modelIn));
            POSTaggerME tagger = new POSTaggerME(model);
            String[] tags = tagger.tag(tokens.toArray(new String[0]));
            posTags = Arrays.asList(tags);
        } catch (IOException e) {
            log.error("Error tagging POS: {}", e.getMessage());
        }

        return posTags;
    }


}
