package com.example.resume.pipeline;
import com.example.resume.core.Stage;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import static com.example.resume.config.Constants.*;

@Slf4j
@Component
public class KeywordSearchStage implements Stage<String, List<String>> {
    private static Tokenizer tokenizer;
    private static POSTaggerME posTagger;
    private static List<String> stopWords;

    @PostConstruct
    public static void init() throws IOException {
        tokenizer = loadTokenizer();
        posTagger = loadPOSTagger();
        stopWords = loadStopWords();
    }

    public List<String> extractKeywords(String inputText){
        Set<String> keywords = new LinkedHashSet<>();
        String[] tokens = tokenizeInputText(inputText, tokenizer);
        String[] posTags = tagPartsOfSpeech(tokens, posTagger);
        extractKeywordsFromPartsOfSpeech(tokens, posTags, stopWords, keywords);
        log.info("Extracted keywords: {}", keywords);
        return new ArrayList<>(keywords);
    }

    private static Tokenizer loadTokenizer() throws IOException {
        try (InputStream tokenModelIn = new FileInputStream(TOKEN_MODEL_PATH)) {
            TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
            return new TokenizerME(tokenModel);
        }
    }

    private static POSTaggerME loadPOSTagger() throws IOException {
        try (InputStream posModelIn = new FileInputStream(POS_MODEL_PATH)) {
            POSModel posModel = new POSModel(posModelIn);
            return new POSTaggerME(posModel);
        }
    }
    private static List<String> loadStopWords() throws IOException {
        try (InputStream stopWordsIn = new FileInputStream(STOPWORDS_PATH)) {
            return IOUtils.readLines(stopWordsIn, StandardCharsets.UTF_8);
        }
    }
    private String[] tokenizeInputText(String inputText, Tokenizer tokenizer) {
        return tokenizer.tokenize(inputText);
    }
    private String[] tagPartsOfSpeech(String[] tokens, POSTaggerME posTagger) {
        return posTagger.tag(tokens);
    }
    private void extractKeywordsFromPartsOfSpeech(String[] tokens, String[] posTags, List<String> stopWords, Set<String> keywords) {
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            String posTag = posTags[i];

            if (isNounOrAdjective(posTag)) {
                token = cleanToken(token);

                if (token.contains("/")) {
                    addKeywordsFromSplit(token, keywords);
                } else {
                    addKeywords(token, stopWords, keywords);
                }
            }
        }
    }

    private boolean isNounOrAdjective(String posTag) {
        return posTag.startsWith("N") || posTag.startsWith("J");
    }
    private String cleanToken(String token) {
        token = token.replaceAll("\\[.*\\]", "");
        return token;
    }

    private void addKeywordsFromSplit(String token, Set<String> keywords) {
        String[] wordsSplit = token.split("/");
        keywords.addAll(Arrays.asList(wordsSplit));
    }

    private void addKeywords(String token, List<String> stopWords, Set<String> keywords) {
        token = token.replaceAll("[^a-zA-Z0-9]", " ").trim().toLowerCase();
        if (!stopWords.contains(token)) {
            keywords.addAll(Arrays.asList(token.split(" ")));
        }
    }

    @Override
    public List<String> process(String input) {
       return extractKeywords(input);

    }
}

