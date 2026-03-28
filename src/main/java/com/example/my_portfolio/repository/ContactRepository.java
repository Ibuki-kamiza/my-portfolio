package com.example.my_portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_portfolio.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByOrderByCreatedAtDesc();
    long countByIsReadFalse();
}