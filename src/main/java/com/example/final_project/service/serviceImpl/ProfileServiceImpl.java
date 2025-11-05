package com.example.final_project.service.serviceImpl;

import com.example.final_project.dto.ProfileUpdateRequest;
import com.example.final_project.entity.Admin;
import com.example.final_project.entity.Student;
import com.example.final_project.entity.Teacher;
import com.example.final_project.repository.AdminRepository;
import com.example.final_project.repository.StudentRepository;
import com.example.final_project.repository.TeacherRepository;
import com.example.final_project.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Transactional
    public void updateProfile(String currentUsername, ProfileUpdateRequest request) {
        Optional<Admin> adminOpt = adminRepository.findByUsername(currentUsername);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            updateUsernameAndAvatar(admin, request.getUsername(), request.getAvatar());
            adminRepository.save(admin);
            return;
        }

        Optional<Teacher> teacherOpt = teacherRepository.findByUsername(currentUsername);
        if (teacherOpt.isPresent()) {
            Teacher teacher = teacherOpt.get();
            updateUsernameAndAvatar(teacher, request.getUsername(), request.getAvatar());
            teacherRepository.save(teacher);
            return;
        }

        Optional<Student> studentOpt = studentRepository.findByUsername(currentUsername);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            updateUsernameAndAvatar(student, request.getUsername(), request.getAvatar());
            studentRepository.save(student);
            return;
        }

        throw new UsernameNotFoundException("User not found: " + currentUsername);
    }

    private void updateUsernameAndAvatar(Object user, String newUsername, String newAvatar) {
        if (user instanceof Admin) {
            ((Admin) user).setUsername(newUsername);
            ((Admin) user).setAvatar(newAvatar);
        } else if (user instanceof Teacher) {
            ((Teacher) user).setUsername(newUsername);
            ((Teacher) user).setAvatar(newAvatar);
        } else if (user instanceof Student) {
            ((Student) user).setUsername(newUsername);
            ((Student) user).setAvatar(newAvatar);
        }
    }
}
