package com.example.resume.util;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DockerManager {

    public static void runDocker() {
        try {

            Path dockerfilePath = Paths.get(System.getProperty("user.home"), "IdeaProjects", "ResumeVisualizationWithNLP", "Dockerfile");

            String imageName = "python-grpc-server";

            buildDockerImage(String.valueOf(dockerfilePath), imageName);

            runDockerContainer(imageName);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void buildDockerImage(String dockerfilePath, String imageName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("docker", "build", "-t", imageName, ".");
        pb.directory(new File(dockerfilePath).getParentFile()); // Устанавливаем рабочую директорию
        Process process = pb.start();

        logProcessOutput(process);
        process.waitFor();
    }

    private static void runDockerContainer(String imageName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("docker", "run", "-d", "-p", "50051:50051", imageName);
        Process process = pb.start();

        logProcessOutput(process);
        process.waitFor();
    }

    private static void logProcessOutput(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("STDOUT: " + line);
            }

            while ((line = errorReader.readLine()) != null) {
                System.err.println("STDERR: " + line);
            }
        }
    }
}

