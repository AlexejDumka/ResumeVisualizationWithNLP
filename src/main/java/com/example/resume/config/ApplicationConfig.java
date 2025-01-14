package com.example.resume.config;
import com.example.resume.grpc.ResumeProcessorClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Slf4j
@Configuration
public class ApplicationConfig {

    public static String getConfigProperty(String propertyName) {
        return loadConfig("src/main/resources/config.properties").getProperty(propertyName);
    }

    public static Properties loadConfig(String configFilePath) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(configFilePath)) {
            properties.load(input);
        } catch (IOException e) {
            log.error("Error loading config file: " + e.getMessage());
        }
        return properties;
    }
        @Bean
        public ManagedChannel managedChannel() {
            return ManagedChannelBuilder.forAddress("localhost", 50051)
                    .usePlaintext()
                    .build();
        }

        @Bean
        public ResumeProcessorClient resumeProcessorClient(ManagedChannel channel) {
            return new ResumeProcessorClient(channel);
        }
    }


