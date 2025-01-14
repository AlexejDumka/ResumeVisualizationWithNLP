package com.example.resume.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity(name = "Section")
@Table(name = "sections", schema = "resume")
public class Section {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "section_content", length = Integer.MAX_VALUE)
    private String sectionContent;

    @Enumerated (EnumType.STRING)
    @Column(name = "section_type", length = 10)
    private SectionType sectionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "resume_id")
    private Resume resume;

}