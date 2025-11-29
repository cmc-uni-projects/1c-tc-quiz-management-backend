package com.example.final_project.service;

import com.example.final_project.dto.ExamHistoryDetailDto;

public interface ExamHistoryService {
    ExamHistoryDetailDto getExamHistoryDetails(Long historyId, Long studentId);
}