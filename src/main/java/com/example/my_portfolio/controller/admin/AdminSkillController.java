package com.example.my_portfolio.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.my_portfolio.entity.Skill;
import com.example.my_portfolio.repository.SkillRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/skills")
@RequiredArgsConstructor
public class AdminSkillController {

    private final SkillRepository skillRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("skills", skillRepository.findAllByOrderByDisplayOrderAsc());
        model.addAttribute("skill", new Skill());
        return "admin/skills";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute Skill skill) {
        skillRepository.save(skill);
        return "redirect:/admin/skills";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        skillRepository.deleteById(id);
        return "redirect:/admin/skills";
    }
}