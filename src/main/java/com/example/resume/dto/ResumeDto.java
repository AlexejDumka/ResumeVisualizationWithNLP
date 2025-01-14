package com.example.resume.dto;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto implements Serializable {
    Long id;
    byte[] pdfFormat;
    byte[] txtFormat;
    byte[] normalizedFormat;
    String name;
    String address;
    String email;
    String phone;
    String linkedinLink;
    String githubLink;
}