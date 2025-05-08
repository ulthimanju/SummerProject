package com.summperproject.iprac.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.summperproject.iprac.entity.Exercise;
import com.summperproject.iprac.entity.Topic;
import com.summperproject.iprac.repository.ExerciseRepository;
import com.summperproject.iprac.repository.TopicRepository;
import com.summperproject.iprac.service.ExerciseService;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, TopicRepository topicRepository) {
        this.exerciseRepository = exerciseRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public Optional<Exercise> getExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    @Override
    public List<Exercise> getExercisesByTopic(Long topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        return topic.map(exerciseRepository::findByTopic).orElse(Collections.emptyList());
    }

    @Override
    public Page<Exercise> getExercisesByTopicAndMaxDifficulty(Long topicId, Integer difficultyLevel, Pageable pageable) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (topic.isPresent()) {
            return exerciseRepository.findByTopicAndDifficultyLevelLessThanEqual(topic.get(), difficultyLevel, pageable);
        }
        return Page.empty();
    }

    @Override
    public List<Exercise> getExercisesByTopicOrderedByDifficulty(Long topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        return topic.map(exerciseRepository::findByTopicOrderByDifficultyLevelAsc).orElse(Collections.emptyList());
    }

    @Override
    public long countExercisesByTopic(Long topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        return topic.map(exerciseRepository::countByTopic).orElse(0L);
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }
}
