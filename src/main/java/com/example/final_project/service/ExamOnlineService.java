package com.example.final_project.service;

import com.example.final_project.dto.ExamOnlineRequest;
import com.example.final_project.dto.ExamOnlineResponse;
import com.example.final_project.dto.ExamOnlineResultsDto;
import java.util.List;

public interface ExamOnlineService {

    ExamOnlineResponse createExamOnline(ExamOnlineRequest request, Long teacherId);
    ExamOnlineResponse startExamOnline(Long examOnlineId, Long teacherId);
    ExamOnlineResultsDto getExamOnlineResults(Long examOnlineId, Long teacherId);
    List<ExamOnlineResponse> getMyOnlineExams(Long teacherId);
    ExamOnlineResponse getExamOnlineById(Long examOnlineId, Long teacherId);
    ExamOnlineResponse updateExamOnline(Long id, ExamOnlineRequest request, Long teacherId);
    ExamOnlineResponse finishExamOnline(Long examOnlineId, Long teacherId);
    void deleteExamOnlineById(Long examOnlineId, Long teacherId);

}