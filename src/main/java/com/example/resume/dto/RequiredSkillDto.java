package com.example.resume.dto;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequiredSkillDto implements Serializable {
    Long id;
    String name;
    PositionDto position;
}