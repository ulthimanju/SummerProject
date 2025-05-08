package com.summperproject.iprac.service;

import java.util.List;
import java.util.Optional;

import com.summperproject.iprac.entity.UserProgress;

public interface UserProgressService {
    UserProgress saveUserProgress(UserProgress userProgress);
    Optional<UserProgress> getUserProgressById(Long id);
    List<UserProgress> getUserProgressByUser(Long userId);
    Optional<UserProgress> getUserProgressByUserAndTopic(Long userId, Long topicId);
    Integer getTotalPointsEarnedByUser(Long userId);
    List<UserProgress> getTopPerformers(int limit);
    Double getAverageCompletionPercentageForTopic(Long topicId);
    List<UserProgress> getAllUserProgress();
    void deleteUserProgress(Long id);
}
