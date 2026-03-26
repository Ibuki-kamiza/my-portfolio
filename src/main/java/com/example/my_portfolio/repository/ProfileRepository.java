package com.example.my_portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_portfolio.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}