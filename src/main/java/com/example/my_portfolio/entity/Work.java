package com.example.my_portfolio.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "works")
@Getter
@Setter
@NoArgsConstructor
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "tech_stack", length = 255)
    private String techStack;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "github_url", length = 255)
    private String githubUrl;

    @Column(name = "demo_url", length = 255)
    private String demoUrl;

    @Column(nullable = false)
    private Boolean published = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}