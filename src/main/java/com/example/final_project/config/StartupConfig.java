package com.example.final_project.config;

import com.example.final_project.entity.Admin;
import com.example.final_project.entity.RoleName;
import com.example.final_project.repository.AdminRepository;
import com.example.final_project.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class StartupConfig {

    @Resource
    FileStorageService storageService;

    @Value("${app.admin.seed.enabled:false}")
    private boolean seedEnabled;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.email:}")
    private String adminEmail;

    @Value("${app.admin.password:}")
    private String adminPassword;

    @Value("${app.admin.role:ADMIN}")
    private String adminRole;

    @Bean
    CommandLineRunner initStorage() {
        return (args) -> storageService.init();
    }

    @Bean
    CommandLineRunner initAdmin(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            if (!seedEnabled) {
                System.out.println("[AdminSeed] Disabled. Skip seeding.");
                return;
            }
            if (adminPassword == null || adminPassword.isBlank()) {
                System.err.println("[AdminSeed] Missing app.admin.password. Skip for safety.");
                return;
            }
            seedAdmin(adminRepository, passwordEncoder);
        };
    }

    @Transactional
    protected void seedAdmin(AdminRepository adminRepository, PasswordEncoder encoder) {
        boolean exists = adminRepository.findByUsername(adminUsername).isPresent()
                || (adminEmail != null && !adminEmail.isBlank()
                    && adminRepository.findByEmail(adminEmail).isPresent());
        if (exists) {
            System.out.println("[AdminSeed] Admin already exists. Skip.");
            return;
        }

        Admin admin = new Admin();
        admin.setUsername(adminUsername);
        admin.setEmail(adminEmail != null ? adminEmail : (adminUsername + "@example.com"));
        admin.setPassword(encoder.encode(adminPassword));
        admin.setRoleName(RoleName.ADMIN); 

        adminRepository.save(admin);
        System.out.println("[AdminSeed] Admin created: " + adminUsername);
    }
}
