package com.example.final_project.service;

import com.example.final_project.dto.QuestionCreateDto;
import com.example.final_project.dto.QuestionResponseDto;
import com.example.final_project.dto.QuestionUpdateDto;
import org.springframework.data.domain.Page;

public interface QuestionService {
    QuestionResponseDto createQuestion(QuestionCreateDto dto);
    QuestionResponseDto updateQuestion(Long id, QuestionUpdateDto dto, String actorUsername);
    QuestionResponseDto getQuestionById(Long id);
    Page<QuestionResponseDto> getAllQuestions(int page, int size);
    void deleteQuestion(Long id, String actorUsername);
}
