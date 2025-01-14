package com.example.resume.pipeline.creators;
import com.example.resume.core.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
@Slf4j
public class ResumeWordCloudCreator implements Stage<Long, File> {

    @Override
    public File process(Long input) {
        return new File(input.toString());
    }
}