package com.example.resume.pipeline;
import com.example.resume.core.Stage;
import org.springframework.stereotype.Component;

@Component
public class TextProcessingStage implements Stage<String,String> {

    @Override
    public String process(String input) {
        return input.toLowerCase();
    }
}
