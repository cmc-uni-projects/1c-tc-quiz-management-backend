package com.example.final_project.service;

public interface PasswordResetService {
    void createPasswordResetTokenForUser(String email);
    void resetPassword(String token, String newPassword);
}
