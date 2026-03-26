package com.example.my_portfolio.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.my_portfolio.entity.Work;
import com.example.my_portfolio.repository.WorkRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/works")
@RequiredArgsConstructor
public class AdminWorksController {

    private final WorkRepository workRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("works", workRepository.findAll());
        return "admin/works/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("work", new Work());
        return "admin/works/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute Work work) {
        work.setPublished(false);
        workRepository.save(work);
        return "redirect:/admin/works";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        workRepository.findById(id)
            .ifPresent(work -> model.addAttribute("work", work));
        return "admin/works/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute Work work) {
        work.setId(id);
        workRepository.save(work);
        return "redirect:/admin/works";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        workRepository.deleteById(id);
        return "redirect:/admin/works";
    }
}