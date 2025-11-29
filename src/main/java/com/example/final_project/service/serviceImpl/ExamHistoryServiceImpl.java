package com.example.final_project.service.serviceImpl;

import com.example.final_project.dto.AnswerResultDto;
import com.example.final_project.dto.ExamHistoryDetailDto;
import com.example.final_project.dto.QuestionResultDto;
import com.example.final_project.entity.Answer;
import com.example.final_project.entity.ExamHistory;
import com.example.final_project.entity.Question;
import com.example.final_project.repository.ExamHistoryRepository;
import com.example.final_project.service.ExamHistoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamHistoryServiceImpl implements ExamHistoryService {

    private final ExamHistoryRepository examHistoryRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = true)
    public ExamHistoryDetailDto getExamHistoryDetails(Long historyId, Long studentId) {
        ExamHistory examHistory = examHistoryRepository.findById(historyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy lịch sử làm bài"));

        if (!examHistory.getStudent().getStudentId().equals(studentId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không có quyền xem chi tiết bài làm này");
        }

        Map<Long, Long> submittedAnswers;
        try {
            submittedAnswers = objectMapper.readValue(examHistory.getSubmittedAnswers(), new TypeReference<Map<Long, Long>>() {});
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi đọc dữ liệu bài làm", e);
        }

        List<QuestionResultDto> questionResults = examHistory.getExam().getExamQuestions().stream()
                .map(examQuestion -> {
                    Question question = examQuestion.getQuestion();
                    Long selectedAnswerId = submittedAnswers.get(question.getId());

                    List<AnswerResultDto> answerResults = question.getAnswers().stream()
                            .map(answer -> new AnswerResultDto(
                                    answer.getId(),
                                    answer.getText(),
                                    answer.isCorrect(),
                                    answer.getId().equals(selectedAnswerId)
                            ))
                            .collect(Collectors.toList());

                    return new QuestionResultDto(question.getId(), question.getTitle(), answerResults);
                })
                .collect(Collectors.toList());

        return ExamHistoryDetailDto.builder()
                .examHistoryId(examHistory.getId())
                .examTitle(examHistory.getExamTitle())
                .displayName(examHistory.getDisplayName())
                .score(examHistory.getScore())
                .submittedAt(examHistory.getSubmittedAt())
                .questions(questionResults)
                .build();
    }
}