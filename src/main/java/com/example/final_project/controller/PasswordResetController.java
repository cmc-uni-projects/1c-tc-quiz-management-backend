package com.example.final_project.controller;

import com.example.final_project.dto.PasswordResetDto;
import com.example.final_project.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            passwordResetService.createPasswordResetTokenForUser(email);
            return ResponseEntity.ok("Password reset link sent to your email.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
        try {
            passwordResetService.resetPassword(passwordResetDto.getToken(), passwordResetDto.getNewPassword());
            return ResponseEntity.ok("Password has been reset successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
