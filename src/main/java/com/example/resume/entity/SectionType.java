package com.example.resume.entity;
import com.example.resume.config.ApplicationConfig;

public enum SectionType {
    UNK("unknown"),
    HEAD("head"),
    SKILL(ApplicationConfig.getConfigProperty("SKILL_SECTION")),
    EDU(ApplicationConfig.getConfigProperty("EDUCATION_SECTION")),
    SUM(ApplicationConfig.getConfigProperty("SUMMARY_SECTION")),
    EXP(ApplicationConfig.getConfigProperty("EXPERIENCE_SECTION")),
    CERT(ApplicationConfig.getConfigProperty("CERTIFICATE_SECTION")),
    LAST("last");

    private final String sections;

    SectionType(String sections) {
        this.sections = sections;
    }

    public boolean contains(String section) {
        if (this.sections == null) {
            return false;
        }
        String[] subSections = this.sections.split("\\|");
        for (String subSection : subSections) {
            if (subSection.equalsIgnoreCase(section.trim())) {
                return true;
            }
        }
        return false;
    }

    public static SectionType identifySectionType(String section) {
        for (SectionType type : SectionType.values()) {
            if (type.contains(section)) {
                return type;
            }
        }
        return UNK;
    }
}
