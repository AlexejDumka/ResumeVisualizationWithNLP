package com.example.resume.dto;
import com.example.resume.entity.SectionType;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto implements Serializable {
    private Integer id;
    private String sectionContent;
    private SectionType sectionType;


}
