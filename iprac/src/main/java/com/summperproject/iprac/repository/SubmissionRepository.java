package com.summperproject.iprac.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.summperproject.iprac.entity.Exercise;
import com.summperproject.iprac.entity.Submission;
import com.summperproject.iprac.entity.User;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUserAndExercise(User user, Exercise exercise);

    Page<Submission> findByUser(User user, Pageable pageable);

    Optional<Submission> findFirstByUserAndExerciseAndIsCorrectTrueOrderBySubmissionTimeDesc(User user, Exercise exercise);

    @Query("SELECT COUNT(DISTINCT s.exercise.id) FROM Submission s WHERE s.user = :user AND s.isCorrect = true")
    long countUniqueCompletedExercises(@Param("user") User user);

    List<Submission> findByUserAndSubmissionTimeBetween(User user, LocalDateTime start, LocalDateTime end);

    @Query("SELECT s FROM Submission s WHERE s.user = :user AND s.isCorrect = true GROUP BY s.exercise.id")
    List<Submission> findAllCorrectSubmissionsByUser(@Param("user") User user);
}
