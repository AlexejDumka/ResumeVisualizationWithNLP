package com.example.resume.config;

import java.util.List;

public class Constants {
    public static final String READ_RESUME_FROM = "Resumes/";
    public static final String SAVE_RESUME_TO = "Resumes/Processed/";

    public static final List<String> SECTIONS = List.of("work experience|experience", "education|academic background", "professional summary|summary", "skills|competencies", "certifications|projects");


    public static final String TOKEN_MODEL_PATH = "src/main/resources/en-token.bin";
    public static final String POS_MODEL_PATH = "src/main/resources/en-pos-maxent.bin";
    public static final String STOPWORDS_PATH = "src/main/resources/stopwords.txt";

}

