package com.example.my_portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_portfolio.entity.Career;

public interface CareerRepository extends JpaRepository<Career, Long> {
    List<Career> findAllByOrderByDisplayOrderAsc();
}