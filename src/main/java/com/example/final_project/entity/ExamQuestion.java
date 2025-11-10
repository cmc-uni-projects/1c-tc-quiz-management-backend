package com.example.final_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exam_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamQuestion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết đến câu hỏi
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}