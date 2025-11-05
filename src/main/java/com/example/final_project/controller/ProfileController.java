package com.example.final_project.controller;

import com.example.final_project.dto.ProfileUpdateRequest;
import com.example.final_project.service.FileStorageService;
import com.example.final_project.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload-avatar")
    public ResponseEntity<Map<String, String>> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            String filename = fileStorageService.save(file);
            // You might want to return the full path depending on your frontend needs
            String fileUrl = "/uploads/" + filename; 
            return ResponseEntity.ok(Map.of("avatarUrl", fileUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Could not upload the file: " + e.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody ProfileUpdateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            profileService.updateProfile(currentUsername, request);
            return ResponseEntity.ok("Profile updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
