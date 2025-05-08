package com.summperproject.iprac.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.summperproject.iprac.entity.Topic;
import com.summperproject.iprac.entity.User;
import com.summperproject.iprac.entity.UserProgress;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    List<UserProgress> findByUser(User user);

    Optional<UserProgress> findByUserAndTopic(User user, Topic topic);

    @Query("SELECT SUM(up.pointsEarned) FROM UserProgress up WHERE up.user = :user")
    Integer getTotalPointsEarned(@Param("user") User user);

    @Query("SELECT up FROM UserProgress up ORDER BY up.pointsEarned DESC")
    List<UserProgress> findTopPerformers(Pageable pageable);

    @Query("SELECT AVG(up.exercisesCompleted * 100.0 / up.totalExercises) FROM UserProgress up WHERE up.topic = :topic")
    Double getAverageCompletionPercentageForTopic(@Param("topic") Topic topic);
}
