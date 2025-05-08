package com.summperproject.iprac.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SubmissionRequestDto {

    @NotNull(message = "Exercise ID is required")
    private Long exerciseId;

    @NotBlank(message = "Submitted code is required")
    private String submittedCode;

    // Constructors
    public SubmissionRequestDto() {
    }

    public SubmissionRequestDto(Long exerciseId, String submittedCode) {
        this.exerciseId = exerciseId;
        this.submittedCode = submittedCode;
    }

    // Getters and Setters
    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getSubmittedCode() {
        return submittedCode;
    }

    public void setSubmittedCode(String submittedCode) {
        this.submittedCode = submittedCode;
    }
}
