package com.example.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamQuestionResponseDto {
    private QuestionResponseDto question;
    private Integer orderIndex;
    private Long examQuestionId;
}
