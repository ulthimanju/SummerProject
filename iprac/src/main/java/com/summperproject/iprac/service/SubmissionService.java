package com.summperproject.iprac.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.summperproject.iprac.entity.Submission;

public interface SubmissionService {
    Submission saveSubmission(Submission submission);
    Optional<Submission> getSubmissionById(Long id);
    List<Submission> getSubmissionsByUserAndExercise(Long userId, Long exerciseId);
    Page<Submission> getSubmissionsByUser(Long userId, Pageable pageable);
    Optional<Submission> getLatestCorrectSubmission(Long userId, Long exerciseId);
    long countUniqueCompletedExercises(Long userId);
    List<Submission> getUserSubmissionsBetweenDates(Long userId, LocalDateTime start, LocalDateTime end);
    List<Submission> getAllCorrectSubmissionsByUser(Long userId);
    List<Submission> getAllSubmissions();
    void deleteSubmission(Long id);
}
