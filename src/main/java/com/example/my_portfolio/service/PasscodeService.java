package com.example.my_portfolio.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.my_portfolio.entity.AdminUser;
import com.example.my_portfolio.repository.AdminUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasscodeService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    private static final int MAX_ATTEMPTS = 5;
    private static final int LOCK_HOURS = 2;

    public boolean isLocked(AdminUser user) {
        if (user.getLockedUntil() == null) return false;
        if (LocalDateTime.now().isAfter(user.getLockedUntil())) {
            user.setLockedUntil(null);
            user.setFailedAttempts(0);
            adminUserRepository.save(user);
            return false;
        }
        return true;
    }

    public boolean verifyPasscode(AdminUser user, String inputPasscode) {
        if (isLocked(user)) return false;

        if (passwordEncoder.matches(inputPasscode, user.getPasscode())) {
            user.setFailedAttempts(0);
            adminUserRepository.save(user);
            return true;
        }

        int attempts = user.getFailedAttempts() + 1;
        user.setFailedAttempts(attempts);

        if (attempts >= MAX_ATTEMPTS) {
            user.setLockedUntil(LocalDateTime.now().plusHours(LOCK_HOURS));
        }

        adminUserRepository.save(user);
        return false;
    }

    public int getRemainingAttempts(AdminUser user) {
        return MAX_ATTEMPTS - user.getFailedAttempts();
    }
}