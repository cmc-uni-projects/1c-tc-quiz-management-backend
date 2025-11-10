package com.example.final_project.service.serviceImpl;

import com.example.final_project.dto.TeacherSearchRequest;
import com.example.final_project.entity.Teacher;
import com.example.final_project.repository.TeacherRepository;
import com.example.final_project.service.EmailService;
import com.example.final_project.service.TeacherService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Page<Teacher> searchTeachers(TeacherSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return teacherRepository.findAll((Specification<Teacher>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(request.getUsername())) {
                predicates.add(criteriaBuilder.like(root.get("username"), "_" + request.getUsername() + "_"));
            }
            if (StringUtils.hasText(request.getEmail())) {
                predicates.add(criteriaBuilder.like(root.get("email"), "_" + request.getEmail() + "_"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    @Override
    public Page<Teacher> findByEmail(String email, Pageable pageable) {
        return teacherRepository.findByEmail(email, pageable);
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public Optional<Teacher> findByUsername(String username) {
        return teacherRepository.findByUsername(username);
    }

    @Override
    public Optional<Teacher> findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    public List<Teacher> getPendingTeachers() {
        try {
            log.info("Bắt đầu lấy danh sách giáo viên chờ duyệt...");
            log.info("Giá trị PENDING enum: {}", Teacher.TeacherStatus.PENDING);
            
            List<Teacher> result = teacherRepository.findByStatus(Teacher.TeacherStatus.PENDING);
            log.info("Tìm thấy {} giáo viên đang chờ duyệt", result.size());
            
            if (!result.isEmpty()) {
                log.info("Giáo viên đầu tiên trong danh sách - ID: {}, Tên: {}, Status: {}", 
                        result.get(0).getTeacherId(), 
                        result.get(0).getUsername(),
                        result.get(0).getStatus());
            }
            
            return result;
        } catch (Exception e) {
            log.error("LỖI KHI LẤY DANH SÁCH GIÁO VIÊN CHỜ DUYỆT", e);
            log.error("Chi tiết lỗi: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể lấy danh sách giáo viên chờ duyệt: " + e.getMessage(), e);
        }
    }

    @Override
    public void approveTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher not found"));
        teacher.setStatus(Teacher.TeacherStatus.APPROVED);
        teacherRepository.save(teacher);
        emailService.sendTeacherApprovalEmail(teacher.getEmail());
    }

    @Override
    public void rejectTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher not found"));
        teacher.setStatus(Teacher.TeacherStatus.REJECTED);
        teacherRepository.save(teacher);
        emailService.sendTeacherRejectionEmail(teacher.getEmail());
    }
}
