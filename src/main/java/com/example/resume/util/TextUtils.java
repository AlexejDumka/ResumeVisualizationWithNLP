package com.example.resume.util;

import com.example.resume.config.ApplicationConfig;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.resume.config.Constants.STOPWORDS_PATH;

public class TextUtils {

    // Метод для загрузки POS модели
    private static POSTaggerME loadPosTagger() throws IOException {
        InputStream modelIn = new FileInputStream(ApplicationConfig.getConfigProperty("POS_MODEL_PATH"));
        POSModel posModel = new POSModel(modelIn);
        return new POSTaggerME(posModel);
    }

    // Метод для выделения биграмм с шаблоном "прилагательное + существительное"
    public static List<String> extractAdjectiveNounBigrams(String text) throws IOException {
        String[] tokens = tokenizeText(text);
        POSTaggerME tagger = loadPosTagger();
        String[] tags = tagger.tag(tokens);

        List<String> specialBigrams = new ArrayList<>();
        for (int i = 0; i < tokens.length - 1; i++) {
            if (tags[i].startsWith("JJ") && tags[i + 1].startsWith("NN")) {
                specialBigrams.add(tokens[i] + " " + tokens[i + 1]);
            }
        }
        return specialBigrams;
    }

    // Метод для выделения триграмм с шаблоном "существительное + глагол + существительное"
    public static List<String> extractNounVerbNounTrigrams(String text) throws IOException {
        String[] tokens = tokenizeText(text);
        POSTaggerME tagger = loadPosTagger();
        String[] tags = tagger.tag(tokens);

        List<String> specialTrigrams = new ArrayList<>();
        for (int i = 0; i < tokens.length - 2; i++) {
            if (tags[i].startsWith("NN") && tags[i + 1].startsWith("VB") && tags[i + 2].startsWith("NN")) {
                specialTrigrams.add(tokens[i] + " " + tokens[i + 1] + " " + tokens[i + 2]);
            }
        }
        return specialTrigrams;
    }

    // Метод для токенизации текста
//    private static String[] tokenizeText(String text) {
//        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
//        return tokenizer.tokenize(text);
//    }


        // Метод для выделения биграмм из текста
        public static List<String> getBigrams(String text) {
            String[] tokens = tokenizeText(text);
            List<String> bigrams = new ArrayList<>();

            for (int i = 0; i < tokens.length - 1; i++) {
                bigrams.add(tokens[i] + " " + tokens[i + 1]);
            }

            return bigrams;
        }

        // Метод для выделения триграмм из текста
        public static List<String> getTrigrams(String text) {
            String[] tokens = tokenizeText(text);
            List<String> trigrams = new ArrayList<>();

            for (int i = 0; i < tokens.length - 2; i++) {
                trigrams.add(tokens[i] + " " + tokens[i + 1] + " " + tokens[i + 2]);
            }

            return trigrams;
        }

        // Метод для токенизации текста
        public static String[] tokenizeText(String text) {
            SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
            return tokenizer.tokenize(text);
        }

        // Метод для сравнения n-грамм из двух текстов (Experience и Skills)
        public static Set<String> findMatchingPhrases(String sourceText, String targetText) {
            List<String> experienceBigrams = getBigrams(sourceText);
            List<String> experienceTrigrams = getTrigrams(sourceText);
            List<String> skillBigrams = getBigrams(targetText);
            List<String> skillTrigrams = getTrigrams(targetText);

            Set<String> matchingPhrases = new HashSet<>();

            // Сравниваем биграммы
            for (String bigram : experienceBigrams) {
                if (skillBigrams.contains(bigram)) {
                    matchingPhrases.add(bigram);
                }
            }

            // Сравниваем триграммы
            for (String trigram : experienceTrigrams) {
                if (skillTrigrams.contains(trigram)) {
                    matchingPhrases.add(trigram);
                }
            }

            return matchingPhrases;
        }

      public static List<String> removeNonAscii(List<String> words) {
        return words.stream()
                .map(word -> Normalizer.normalize(word, Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", ""))
                .collect(Collectors.toList());
    }

    public static List<String> toLowerCase(List<String> words) {
        return words.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public static List<String> removePunctuation(List<String> words) {
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

    public static List<String> removeStopWords(List<String> words) throws IOException {
        List<String> stopWords = loadStopWords();
        return words.stream()
                .filter(word -> !stopWords.contains(word))
                .collect(Collectors.toList());
    }


}
