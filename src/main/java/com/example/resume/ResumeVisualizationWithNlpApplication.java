package com.example.resume;

import com.example.resume.grpc.GRPC;
import com.example.resume.grpc.ResumeProcessorClient;
import com.example.resume.pipeline.PipelineProcessor;
import com.example.resume.util.DockerManager;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ResumeVisualizationWithNlpApplication implements CommandLineRunner {

    @Autowired
    private PipelineProcessor pipelineProcessor;
    public static void main(String[] args) {
        SpringApplication.run(ResumeVisualizationWithNlpApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
      //  DockerManager.runDocker();
        String directoryPath = "Resumes/";
        File inputDirectory = new File(directoryPath);

        if (inputDirectory.exists() && inputDirectory.isDirectory()) {
            String result = pipelineProcessor.process(inputDirectory);
            System.out.println("Pipeline processing result: \n" + result);
        } else {
            System.err.println("Invalid directory path: " + directoryPath);
        }

    }
}
