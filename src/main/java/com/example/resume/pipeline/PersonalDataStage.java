package com.example.resume.pipeline;
import com.example.resume.core.Stage;
import com.example.resume.dto.PersonalInfoDto;
import com.example.resume.entity.Resume;
import com.example.resume.mapper.ResumeMapper;
import com.example.resume.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class PersonalDataStage implements Stage<Long, Long> {
    @Autowired
    private ResumeService resumeService;
    @Override
      public Long process(Long resumeId) {
        // return new Hashtable<>();
        String content = getContent(resumeId);
        PersonalInfoDto personalInfoDto = new PersonalInfoDto();
        personalInfoDto.setAddress("");
        personalInfoDto.setEmail(extractEmail(content));
        personalInfoDto.setPhone(extractPhoneNumber(content));
        personalInfoDto.setName(extractName(content));
        personalInfoDto.setLinkedinLink(extractLinkedIn(content));
        personalInfoDto.setGithubLink(extractGitHub(content));
        Resume resume = getResumeById(resumeId);
        ResumeMapper.INSTANCE.fromPersonalInfoDtoToResume(personalInfoDto);
        Resume result = resumeService.update(resume);
            return result.getId();
    }
    public  Resume getResumeById(Long resumeId) {
        return Optional.ofNullable(resumeService.findById(resumeId)).orElseThrow(RuntimeException::new);

    }
    public  String getContent(Long resumeId) {
    Resume resume = getResumeById(resumeId);
    return new String(resume.getTxtFormat(), StandardCharsets.UTF_8);
    }


    public static String extractName(String text) {
        String[] lines = text.split("\n");
        if (lines.length > 0) {
            String firstLine = lines[0].trim();
            String regex = "^[A-Z]+\\s+[A-Z]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(firstLine);

            if (matcher.find()) {
                log.info("Name found: " + matcher.group());
                return matcher.group();
            }
        }
        return null;
    }
    public static String extractEmail(String text) {
        String regex = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            log.info("Email found: " + matcher.group());
            return matcher.group();
        }
        return null;
    }

    public static String extractLinkedIn(String text) {
        String regex = "\\bhttps?://(www\\.)?linkedin\\.com/in/[A-Za-z0-9_-]+\\b|\\blinkedin\\.com/in/[A-Za-z0-9_-]+\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            log.info("LinkedIn url found: " + matcher.group());
            return matcher.group();
        }
        return null;
    }

    public static String extractGitHub(String text) {
        String regex = "\\bhttps?://(www\\.)?github\\.com/[A-Za-z0-9_-]+\\b|\\bgithub\\.com/[A-Za-z0-9_-]+\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            log.info("GitHub url found: " + matcher.group());
            return matcher.group();
        }
        return null;
    }

    public static String extractPhoneNumber(String text) {
        String regex = "\\(\\d{3}\\) \\d{3}-\\d{4}|\\d{3} \\d{3} \\d{4}|\\d{3}-\\d{3}-\\d{4}|\\d{10}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            log.info("Contact phone number found: " + matcher.group());
            return matcher.group();
        }
        return null;
    }
}


