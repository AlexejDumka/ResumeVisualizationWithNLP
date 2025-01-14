package com.example.resume.pipeline.extractors;
import com.example.resume.core.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResumePersonalInfoExtractor implements Stage<Long, Long> {

    @Override
    public Long process(Long input){
        return null;
    }
}

