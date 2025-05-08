package com.summperproject.iprac.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.summperproject.iprac.entity.Exercise;
import com.summperproject.iprac.entity.Topic;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByTopic(Topic topic);
    Page<Exercise> findByTopicAndDifficultyLevelLessThanEqual(Topic topic, Integer difficultyLevel, Pageable pageable);
    List<Exercise> findByTopicOrderByDifficultyLevelAsc(Topic topic);
    long countByTopic(Topic topic);
}
