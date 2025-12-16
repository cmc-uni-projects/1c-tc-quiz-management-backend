package com.example.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAnswerDto {
    private Long questionId;
    private Long answerId;
    @com.fasterxml.jackson.annotation.JsonProperty("isCorrect")
    private boolean isCorrect;
}
