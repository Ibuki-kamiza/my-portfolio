package com.example.my_portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.my_portfolio.repository.ProfileRepository;
import com.example.my_portfolio.repository.SkillRepository;
import com.example.my_portfolio.repository.WorkRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProfileRepository profileRepository;
    private final SkillRepository skillRepository;
    private final WorkRepository workRepository;

    @GetMapping("/")
    public String index(Model model) {
        profileRepository.findAll().stream().findFirst()
            .ifPresent(profile -> model.addAttribute("profile", profile));
        model.addAttribute("skills", skillRepository.findAllByOrderByDisplayOrderAsc());
        model.addAttribute("works", workRepository.findByPublishedTrueOrderByCreatedAtDesc());
        return "index";
    }
}