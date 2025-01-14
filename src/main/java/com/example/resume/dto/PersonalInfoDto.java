package com.example.resume.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoDto {
    String name;
    String address;
    String email;
    String phone;
    String linkedinLink;
    String githubLink;
}
