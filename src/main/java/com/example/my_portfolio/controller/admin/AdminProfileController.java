package com.example.my_portfolio.controller.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.my_portfolio.entity.Profile;
import com.example.my_portfolio.repository.AdminUserRepository;
import com.example.my_portfolio.repository.ProfileRepository;
import com.example.my_portfolio.service.FileUploadService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/profile")
@RequiredArgsConstructor
public class AdminProfileController {

    private final ProfileRepository profileRepository;
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadService fileUploadService;

    @GetMapping
    public String edit(Model model) {
        Profile profile = profileRepository.findAll()
            .stream().findFirst()
            .orElse(new Profile());
        model.addAttribute("profile", profile);
        return "admin/profile";
    }

    @PostMapping
    public String save(@ModelAttribute Profile profile,
                       @RequestParam(required = false) String newPasscode,
                       @RequestParam(required = false) MultipartFile imageFile,
                       Authentication authentication) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = fileUploadService.uploadProfileImage(imageFile);
                if (imageUrl != null) {
                    profile.setAvatarUrl(imageUrl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        profileRepository.findAll().stream().findFirst().ifPresent(existing -> {
            profile.setId(existing.getId());
            if (profile.getAvatarUrl() == null || profile.getAvatarUrl().isEmpty()) {
                profile.setAvatarUrl(existing.getAvatarUrl());
            }
        });

        profileRepository.save(profile);

        if (newPasscode != null && newPasscode.matches("[0-9]{6}")) {
            adminUserRepository.findByUsername(authentication.getName())
                .ifPresent(admin -> {
                    admin.setPasscode(passwordEncoder.encode(newPasscode));
                    admin.setFailedAttempts(0);
                    admin.setLockedUntil(null);
                    adminUserRepository.save(admin);
                });
        }

        return "redirect:/admin/profile?saved";
    }
}