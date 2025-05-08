package com.summperproject.iprac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.summperproject.iprac.entity.Exercise;
import com.summperproject.iprac.service.ExerciseService;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseService.getExerciseById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<Exercise>> getExercisesByTopic(@PathVariable Long topicId) {
        List<Exercise> exercises = exerciseService.getExercisesByTopic(topicId);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/topic/{topicId}/difficulty/{difficultyLevel}")
    public ResponseEntity<Page<Exercise>> getExercisesByTopicAndMaxDifficulty(
            @PathVariable Long topicId,
            @PathVariable Integer difficultyLevel,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercises = exerciseService.getExercisesByTopicAndMaxDifficulty(topicId, difficultyLevel, pageable);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/topic/{topicId}/ordered")
    public ResponseEntity<List<Exercise>> getExercisesByTopicOrderedByDifficulty(@PathVariable Long topicId) {
        List<Exercise> exercises = exerciseService.getExercisesByTopicOrderedByDifficulty(topicId);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/topic/{topicId}/count")
    public ResponseEntity<Long> countExercisesByTopic(@PathVariable Long topicId) {
        long count = exerciseService.countExercisesByTopic(topicId);
        return ResponseEntity.ok(count);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        Exercise createdExercise = exerciseService.saveExercise(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExercise);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody Exercise exercise) {
        return exerciseService.getExerciseById(id)
                .map(existingExercise -> {
                    exercise.setId(id);
                    Exercise updatedExercise = exerciseService.saveExercise(exercise);
                    return ResponseEntity.ok(updatedExercise);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        if (exerciseService.getExerciseById(id).isPresent()) {
            exerciseService.deleteExercise(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
