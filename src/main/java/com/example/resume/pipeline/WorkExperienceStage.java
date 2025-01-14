package com.example.resume.pipeline;
import com.example.resume.core.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util .*;
import java.util.regex .*;



@Slf4j
@Component
public class WorkExperienceStage implements Stage<String, Map<String,String>> {

    @Override
    public Map<String, String> process(String input) {
        Map<String, String> result = parseWorkExperience(input);
        printResult(result);
        return result;
    }

        private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{1,2}/\\d{4}|\\w+ \\d{4})\\s*-\\s*(\\d{1,2}/\\d{4}|\\w+ \\d{4}|Present|Current)");
        private static final Pattern COMBINED_PATTERN = Pattern.compile("(\\d{1,2}/\\d{4}|\\w+ \\d{4})\\s*-\\s*(\\d{1,2}/\\d{4}|\\w+ \\d{4}|Present|Current)\\s+(.*)\\s+(.*)");

        public static Map<String, String> parseWorkExperience(String text) {
            Map<String, String> workExperiences = new LinkedHashMap<>();
            String[] lines = text.split("\\n");

            for (int i = 0; i < lines.length; i++) {
                String line = lines[i].trim();
                Matcher matcher = COMBINED_PATTERN.matcher(line);
                if (matcher.find()) {
                    processMatch(matcher, workExperiences);
                } else {
                    Matcher dateMatcher = DATE_PATTERN.matcher(line);
                    if (dateMatcher.find()) {
                        String periodFrom = dateMatcher.group(1).trim();
                        String periodTo = dateMatcher.group(2).trim();

                        int dateStartIndex = dateMatcher.start();
                        String leftPart = line.substring(0, dateStartIndex).trim();
                        String rightPart = line.substring(dateMatcher.end()).trim();

                        String position = extractPosition(leftPart);
                        String companyName = rightPart;

                        if (isValidPeriod(periodFrom, periodTo)) {
                            String key = String.format("%s|%s|%s|%s", periodFrom, periodTo, position, companyName);
                            workExperiences.put(key, "Processed");
                        }
                    } else if (i < lines.length - 1) {
                        String nextLine = lines[i + 1].trim();
                        Matcher nextLineMatcher = DATE_PATTERN.matcher(nextLine);
                        if (nextLineMatcher.find()) {
                            String periodFrom = nextLineMatcher.group(1).trim();
                            String periodTo = nextLineMatcher.group(2).trim();

                            String position = line;
                            String companyName = nextLine;

                            if (isValidPeriod(periodFrom, periodTo)) {
                                String key = String.format("%s|%s|%s|%s", periodFrom, periodTo, position, companyName);
                                workExperiences.put(key, "Processed");
                                i++;                             }
                        }
                    }
                }
            }

            return workExperiences;
        }

        private static void processMatch(Matcher matcher, Map<String, String> workExperiences) {
            String periodFrom = matcher.group(1).trim();
            String periodTo = matcher.group(2).trim();
            String position = matcher.group(3).trim();
            String companyName = matcher.group(4).trim();

            if (isValidPeriod(periodFrom, periodTo)) {
                String key = String.format("%s|%s|%s|%s", periodFrom, periodTo, position, companyName);
                workExperiences.put(key, "Processed");
            }
        }

        private static String extractPosition(String leftPart) {
            int firstComma = leftPart.indexOf(',');
            if (firstComma != -1) {
                return leftPart.substring(0, firstComma).trim();
            }
            return leftPart;
        }

        private static boolean isValidPeriod(String periodFrom, String periodTo) {
          //TODO Implement this method
           return true;
        }

        private static void printResult(Map<String, String> result) {
            for (Map.Entry<String, String> entry : result.entrySet()) {
                System.out.printf("Key: %s, Value: %s%n", entry.getKey(), entry.getValue());
            }
        }
    }
