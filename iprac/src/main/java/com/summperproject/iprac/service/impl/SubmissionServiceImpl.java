package com.summperproject.iprac.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.summperproject.iprac.entity.Exercise;
import com.summperproject.iprac.entity.Submission;
import com.summperproject.iprac.entity.User;
import com.summperproject.iprac.repository.ExerciseRepository;
import com.summperproject.iprac.repository.SubmissionRepository;
import com.summperproject.iprac.repository.UserRepository;
import com.summperproject.iprac.service.SubmissionService;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public SubmissionServiceImpl(SubmissionRepository submissionRepository, UserRepository userRepository,
                                 ExerciseRepository exerciseRepository) {
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public Submission saveSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }

    @Override
    public Optional<Submission> getSubmissionById(Long id) {
        return submissionRepository.findById(id);
    }

    @Override
    public List<Submission> getSubmissionsByUserAndExercise(Long userId, Long exerciseId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Exercise> exercise = exerciseRepository.findById(exerciseId);

        if (user.isPresent() && exercise.isPresent()) {
            return submissionRepository.findByUserAndExercise(user.get(), exercise.get());
        }
        return Collections.emptyList();
    }

    @Override
    public Page<Submission> getSubmissionsByUser(Long userId, Pageable pageable) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return submissionRepository.findByUser(user.get(), pageable);
        }
        return Page.empty();
    }

    @Override
    public Optional<Submission> getLatestCorrectSubmission(Long userId, Long exerciseId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Exercise> exercise = exerciseRepository.findById(exerciseId);

        if (user.isPresent() && exercise.isPresent()) {
            return submissionRepository.findFirstByUserAndExerciseAndIsCorrectTrueOrderBySubmissionTimeDesc(
                    user.get(), exercise.get());
        }
        return Optional.empty();
    }

    @Override
    public long countUniqueCompletedExercises(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(submissionRepository::countUniqueCompletedExercises).orElse(0L);
    }

    @Override
    public List<Submission> getUserSubmissionsBetweenDates(Long userId, LocalDateTime start, LocalDateTime end) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return submissionRepository.findByUserAndSubmissionTimeBetween(user.get(), start, end);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Submission> getAllCorrectSubmissionsByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return submissionRepository.findAllCorrectSubmissionsByUser(user.get());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public void deleteSubmission(Long id) {
        submissionRepository.deleteById(id);
    }
}
