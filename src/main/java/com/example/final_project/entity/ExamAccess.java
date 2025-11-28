package com.example.final_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "exam_access")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ExamAccess.ExamAccessId.class)
public class ExamAccess {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExamAccessId implements Serializable {
        private Long exam;
        private Long student;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ExamAccessId that = (ExamAccessId) o;
            return Objects.equals(exam, that.exam) && Objects.equals(student, that.student);
        }

        @Override
        public int hashCode() {
            return Objects.hash(exam, student);
        }
    }
}
