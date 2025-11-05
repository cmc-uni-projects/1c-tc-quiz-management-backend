package com.example.final_project.service;

import com.example.final_project.dto.ProfileUpdateRequest;

public interface ProfileService {
    void updateProfile(String currentUsername, ProfileUpdateRequest request);
}
