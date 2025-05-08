package com.summperproject.iprac.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.summperproject.iprac.entity.Topic;
import com.summperproject.iprac.entity.User;
import com.summperproject.iprac.entity.UserProgress;
import com.summperproject.iprac.repository.TopicRepository;
import com.summperproject.iprac.repository.UserProgressRepository;
import com.summperproject.iprac.repository.UserRepository;
import com.summperproject.iprac.service.UserProgressService;

@Service
public class UserProgressServiceImpl implements UserProgressService {

    private final UserProgressRepository userProgressRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public UserProgressServiceImpl(UserProgressRepository userProgressRepository,
                                   UserRepository userRepository,
                                   TopicRepository topicRepository) {
        this.userProgressRepository = userProgressRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public UserProgress saveUserProgress(UserProgress userProgress) {
        return userProgressRepository.save(userProgress);
    }

    @Override
    public Optional<UserProgress> getUserProgressById(Long id) {
        return userProgressRepository.findById(id);
    }

    @Override
    public List<UserProgress> getUserProgressByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(userProgressRepository::findByUser).orElse(Collections.emptyList());
    }

    @Override
    public Optional<UserProgress> getUserProgressByUserAndTopic(Long userId, Long topicId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Topic> topic = topicRepository.findById(topicId);

        if (user.isPresent() && topic.isPresent()) {
            return userProgressRepository.findByUserAndTopic(user.get(), topic.get());
        }
        return Optional.empty();
    }

    @Override
    public Integer getTotalPointsEarnedByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(userProgressRepository::getTotalPointsEarned).orElse(0);
    }

    @Override
    public List<UserProgress> getTopPerformers(int limit) {
        return userProgressRepository.findTopPerformers(PageRequest.of(0, limit));
    }

    @Override
    public Double getAverageCompletionPercentageForTopic(Long topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        return topic.map(userProgressRepository::getAverageCompletionPercentageForTopic).orElse(0.0);
    }

    @Override
    public List<UserProgress> getAllUserProgress() {
        return userProgressRepository.findAll();
    }

    @Override
    public void deleteUserProgress(Long id) {
        userProgressRepository.deleteById(id);
    }
}
