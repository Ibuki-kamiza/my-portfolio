package com.example.my_portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.my_portfolio.repository.WorkRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/works")
@RequiredArgsConstructor
public class WorksController {

    private final WorkRepository workRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("works", workRepository.findByPublishedTrueOrderByCreatedAtDesc());
        return "works/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        workRepository.findById(id)
            .ifPresent(work -> model.addAttribute("work", work));
        return "works/show";
    }
}