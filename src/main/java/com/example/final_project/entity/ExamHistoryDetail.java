package com.example.final_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exam_history_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamHistoryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_history_id", nullable = false)
    private ExamHistory examHistory;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "answer_id", nullable = false)
    private Long answerId; // ID of the answer selected by the student

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;
}
