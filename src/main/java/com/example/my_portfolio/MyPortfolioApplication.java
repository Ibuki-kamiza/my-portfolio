package com.example.my_portfolio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.my_portfolio.entity.AdminUser;
import com.example.my_portfolio.repository.AdminUserRepository;

@SpringBootApplication
public class MyPortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyPortfolioApplication.class, args);
	}

	@Bean
	public CommandLineRunner initAdmin(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (adminUserRepository.count() == 0) {
				AdminUser admin = new AdminUser();
				admin.setUsername("ibuki");
				admin.setPassword(passwordEncoder.encode("7261Ibuki"));
				adminUserRepository.save(admin);
				System.out.println("管理者ユーザーを作成しました");
			}
		};
	}
}