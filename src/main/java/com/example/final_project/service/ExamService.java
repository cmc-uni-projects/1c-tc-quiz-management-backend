package com.example.final_project.service;

import com.example.final_project.dto.ExamRequestDto;
import com.example.final_project.dto.ExamResponseDto;

import java.util.List;

public interface ExamService {
    ExamResponseDto createExam(ExamRequestDto dto, Long teacherId);
    ExamResponseDto updateExam(Long examId, ExamRequestDto dto, Long teacherId);
    List<ExamResponseDto> getExamsByTeacher(Long teacherId);
    ExamResponseDto getExamById(Long examId, Long teacherId);
    void deleteExamById(Long examId, Long teacherId);
}