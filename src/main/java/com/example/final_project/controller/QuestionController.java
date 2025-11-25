package com.example.final_project.controller;

import com.example.final_project.dto.*;
import com.example.final_project.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<QuestionResponseDto> create(@Valid @RequestBody QuestionCreateDto dto) {
        QuestionResponseDto q = questionService.createQuestion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(q);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<QuestionResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<QuestionResponseDto>> list(@RequestParam(defaultValue = "0") int page) {
        Page<QuestionResponseDto> p = questionService.getAllQuestions(page, 10);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<QuestionResponseDto> update(@PathVariable Long id,
                                    @Valid @RequestBody QuestionUpdateDto dto,
                                    @RequestHeader(value = "X-User", required = false) String actor) {
        if (actor == null) actor = dto.getAnswers().isEmpty() ? "unknown" : "unknown";
        QuestionResponseDto updated = questionService.updateQuestion(id, dto, actor);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @RequestHeader(value = "X-User", required = false) String actor) {
        if (actor == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Missing X-User header");
        questionService.deleteQuestion(id, actor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/question-types")
    public ResponseEntity<com.example.final_project.entity.QuestionType[]> getQuestionTypes() {
        return ResponseEntity.ok(com.example.final_project.entity.QuestionType.values());
    }

    @GetMapping("/difficulties")
    public ResponseEntity<java.util.List<String>> getDifficulties() {
        return ResponseEntity.ok(java.util.Arrays.asList("Easy", "Medium", "Hard"));
    }
}