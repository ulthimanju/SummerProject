package com.summperproject.iprac.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.summperproject.iprac.entity.Exercise;

public interface ExerciseService {
    Exercise saveExercise(Exercise exercise);
    Optional<Exercise> getExerciseById(Long id);
    List<Exercise> getExercisesByTopic(Long topicId);
    Page<Exercise> getExercisesByTopicAndMaxDifficulty(Long topicId, Integer difficultyLevel, Pageable pageable);
    List<Exercise> getExercisesByTopicOrderedByDifficulty(Long topicId);
    long countExercisesByTopic(Long topicId);
    List<Exercise> getAllExercises();
    void deleteExercise(Long id);
}
