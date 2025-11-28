package com.example.final_project.controller;

import com.example.final_project.dto.ExamResponseDto;
import com.example.final_project.entity.Student;
import com.example.final_project.service.CustomUserDetails;
import com.example.final_project.service.ExamService;
import com.example.final_project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ExamService examService;

    @PostMapping("/start-exam/{examId}")
    public ResponseEntity<String> startExam(@PathVariable Long examId, Principal principal) {
        Long studentId = getAuthenticatedUserId(principal);
        ExamResponseDto exam = examService.getExamById(examId, studentId);
        // If the student is authorized, the above line will not throw an exception.
        return ResponseEntity.ok("Bạn được phép làm bài thi '" + exam.getTitle() + "'. Chúc bạn may mắn!");
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }

    @GetMapping
    public Page<Student> getAllStudents(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            Pageable pageable) {

        // Nếu có email hoặc username riêng biệt, ưu tiên sử dụng chúng
        if (email != null && !email.isEmpty() && username != null && !username.isEmpty()) {
            // Tìm kiếm cả email và username
            return studentService.searchByEmailAndUsername(email, username, pageable);
        } else if (email != null && !email.isEmpty()) {
            // Chỉ tìm theo email
            return studentService.searchByEmail(email, pageable);
        } else if (username != null && !username.isEmpty()) {
            // Chỉ tìm theo username
            return studentService.searchByUsername(username, pageable);
        } else if (keyword != null && !keyword.isEmpty()) {
            // Tìm theo keyword chung (cũ)
            return studentService.searchStudent(keyword, pageable);
        } else {
            // Trả về tất cả
            return studentService.searchStudent("", pageable);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.findById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.save(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> studentOptional = studentService.findById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setUsername(studentDetails.getUsername());
            student.setEmail(studentDetails.getEmail());
            student.setPassword(studentDetails.getPassword());
            return ResponseEntity.ok(studentService.save(student));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Long getAuthenticatedUserId(Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Không xác thực được người dùng");
        }
        Authentication authentication = (Authentication) principal;
        Object principalObject = authentication.getPrincipal();
        if (principalObject instanceof CustomUserDetails) {
            return ((CustomUserDetails) principalObject).getId();
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Loại principal người dùng không hợp lệ. Không phải CustomUserDetails.");
        }
    }
}