package com.summperproject.iprac.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.summperproject.iprac.dto.SubmissionRequestDto;
import com.summperproject.iprac.entity.Exercise;
import com.summperproject.iprac.entity.Submission;
import com.summperproject.iprac.entity.User;
import com.summperproject.iprac.security.UserDetailsImpl;
import com.summperproject.iprac.service.ExerciseService;
import com.summperproject.iprac.service.SubmissionService;
import com.summperproject.iprac.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;
    private final UserService userService;
    private final ExerciseService exerciseService;

    @Autowired
    public SubmissionController(SubmissionService submissionService,
                                UserService userService,
                                ExerciseService exerciseService) {
        this.submissionService = submissionService;
        this.userService = userService;
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<?> submitSolution(@Valid @RequestBody SubmissionRequestDto submissionRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userService.getUserById(userDetails.getId());
        Optional<Exercise> exerciseOptional = exerciseService.getExerciseById(submissionRequest.getExerciseId());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (exerciseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exercise not found");
        }

        User user = userOptional.get();
        Exercise exercise = exerciseOptional.get();

        // Create submission
        Submission submission = new Submission();
        submission.setUser(user);
        submission.setExercise(exercise);
        submission.setSubmittedCode(submissionRequest.getSubmittedCode());
        submission.setSubmissionTime(LocalDateTime.now());

        // TODO: Implement code execution and evaluation logic
        // For now, just mark as correct for demo purposes
        submission.setIsCorrect(true);
        submission.setExecutionTimeMs(100L);
        submission.setFeedback("Your solution is correct!");

        Submission savedSubmission = submissionService.saveSubmission(submission);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubmission);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Submission>> getAllSubmissions() {
        List<Submission> submissions = submissionService.getAllSubmissions();
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<Submission> submissionOptional = submissionService.getSubmissionById(id);

        if (submissionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Submission submission = submissionOptional.get();

        // Only allow users to access their own submissions unless they're an admin
        if (!submission.getUser().getId().equals(userDetails.getId()) &&
            !userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(submission);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<Submission>> getCurrentUserSubmissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Submission> submissions = submissionService.getSubmissionsByUser(userDetails.getId(), pageable);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/user/exercise/{exerciseId}")
    public ResponseEntity<List<Submission>> getUserSubmissionsByExercise(
            @PathVariable Long exerciseId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Submission> submissions = submissionService.getSubmissionsByUserAndExercise(
                userDetails.getId(), exerciseId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/user/correct")
    public ResponseEntity<List<Submission>> getAllUserCorrectSubmissions(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Submission> submissions = submissionService.getAllCorrectSubmissionsByUser(userDetails.getId());
        return ResponseEntity.ok(submissions);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        if (submissionService.getSubmissionById(id).isPresent()) {
            submissionService.deleteSubmission(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
