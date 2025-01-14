package com.example.resume.dto;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionDto implements Serializable {
    Long id;
    String positionName;
}