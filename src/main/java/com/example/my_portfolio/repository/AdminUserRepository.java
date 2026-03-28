package com.example.my_portfolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_portfolio.entity.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByUsername(String username);
    boolean existsByUsername(String username);
}