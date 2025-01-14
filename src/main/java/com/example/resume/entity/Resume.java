package com.example.resume.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "resumes", schema = "resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JdbcTypeCode(Types.BINARY)
    @Column(name = "pdf_format")
    private byte[] pdfFormat;

    @JdbcTypeCode(Types.BINARY)

    @Column(name = "txt_format")
    private byte[] txtFormat;

    @JdbcTypeCode(Types.BINARY)
    @Column(name = "normalized_format")
    private byte[] normalizedFormat;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Section> sections = new LinkedHashSet<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Skill> skills = new LinkedHashSet<>();
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "linkedin_link")
    private String linkedinLink;

    @Column(name = "github_link")
    private String githubLink;

}
