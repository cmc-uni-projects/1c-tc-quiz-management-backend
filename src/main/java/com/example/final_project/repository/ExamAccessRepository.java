package com.example.final_project.repository;

import com.example.final_project.entity.Exam;
import com.example.final_project.entity.ExamAccess;
import com.example.final_project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamAccessRepository extends JpaRepository<ExamAccess, ExamAccess.ExamAccessId> {
    boolean existsByExamAndStudent(Exam exam, Student student);
    void deleteByExam(Exam exam);
}
