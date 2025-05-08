package com.summperproject.iprac.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.summperproject.iprac.entity.UserProgress;
import com.summperproject.iprac.security.UserDetailsImpl;
import com.summperproject.iprac.service.UserProgressService;

@RestController
@RequestMapping("/api/user-progress")
public class UserProgressController {

    private final UserProgressService userProgressService;

    @Autowired
    public UserProgressController(UserProgressService userProgressService) {
        this.userProgressService = userProgressService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<UserProgress>> getCurrentUserProgress(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<UserProgress> userProgress = userProgressService.getUserProgressByUser(userDetails.getId());
        return ResponseEntity.ok(userProgress);
    }

    @GetMapping("/me/topic/{topicId}")
    public ResponseEntity<?> getCurrentUserProgressForTopic(
            @PathVariable Long topicId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        var progressOptional = userProgressService.getUserProgressByUserAndTopic(userDetails.getId(), topicId);
        if (progressOptional.isPresent()) {
            return ResponseEntity.ok(progressOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No progress found for this topic"));
        }
    }

    @GetMapping("/me/points")
    public ResponseEntity<Integer> getCurrentUserTotalPoints(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Integer totalPoints = userProgressService.getTotalPointsEarnedByUser(userDetails.getId());
        return ResponseEntity.ok(totalPoints);
    }

    @GetMapping("/top-performers")
    public ResponseEntity<List<UserProgress>> getTopPerformers(@RequestParam(defaultValue = "10") int limit) {
        List<UserProgress> topPerformers = userProgressService.getTopPerformers(limit);
        return ResponseEntity.ok(topPerformers);
    }

    @GetMapping("/topic/{topicId}/completion-percentage")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getAverageCompletionPercentageForTopic(@PathVariable Long topicId) {
        Double averagePercentage = userProgressService.getAverageCompletionPercentageForTopic(topicId);
        return ResponseEntity.ok(averagePercentage);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserProgress>> getUserProgressByUserId(@PathVariable Long userId) {
        List<UserProgress> userProgress = userProgressService.getUserProgressByUser(userId);
        return ResponseEntity.ok(userProgress);
    }

    @GetMapping("/user/{userId}/topic/{topicId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserProgressByUserIdAndTopicId(
            @PathVariable Long userId,
            @PathVariable Long topicId) {
        var progressOptional = userProgressService.getUserProgressByUserAndTopic(userId, topicId);
        if (progressOptional.isPresent()) {
            return ResponseEntity.ok(progressOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No progress found for this user and topic"));
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserProgress>> getAllUserProgress() {
        List<UserProgress> allUserProgress = userProgressService.getAllUserProgress();
        return ResponseEntity.ok(allUserProgress);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserProgress(@PathVariable Long id) {
        userProgressService.deleteUserProgress(id);
        return ResponseEntity.noContent().build();
    }
}
