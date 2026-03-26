package com.example.my_portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_portfolio.entity.Work;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByPublishedTrueOrderByCreatedAtDesc();
}