package com.example.my_portfolio.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.my_portfolio.entity.Career;
import com.example.my_portfolio.repository.CareerRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/careers")
@RequiredArgsConstructor
public class AdminCareerController {

    private final CareerRepository careerRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("careers", careerRepository.findAllByOrderByDisplayOrderAsc());
        model.addAttribute("career", new Career());
        return "admin/careers";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute Career career) {
        careerRepository.save(career);
        return "redirect:/admin/careers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        careerRepository.deleteById(id);
        return "redirect:/admin/careers";
    }
}