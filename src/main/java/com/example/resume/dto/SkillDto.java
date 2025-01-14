package com.example.resume.dto;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillDto implements Serializable {
    Long id;
    String skillName;
}