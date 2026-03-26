package com.example.my_portfolio.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "github_url", length = 255)
    private String githubUrl;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}