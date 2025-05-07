package com.summperproject.iprac.model;

import java.util.List;
import java.util.Objects;

public class GeminiResponseDto {
    private List<Candidate> candidates;
    private PromptFeedback promptFeedback;

    // Getters and setters
    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public PromptFeedback getPromptFeedback() {
        return promptFeedback;
    }

    public void setPromptFeedback(PromptFeedback promptFeedback) {
        this.promptFeedback = promptFeedback;
    }

    // toString, equals, and hashCode methods
    @Override
    public String toString() {
        return "GeminiResponseDto{" +
                "candidates=" + candidates +
                ", promptFeedback=" + promptFeedback +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeminiResponseDto that = (GeminiResponseDto) o;
        return Objects.equals(candidates, that.candidates) &&
               Objects.equals(promptFeedback, that.promptFeedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidates, promptFeedback);
    }

    public static class Candidate {
        private Content content;
        private String finishReason;
        private int index;
        private List<SafetyRating> safetyRatings;

        // Getters and setters
        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public List<SafetyRating> getSafetyRatings() {
            return safetyRatings;
        }

        public void setSafetyRatings(List<SafetyRating> safetyRatings) {
            this.safetyRatings = safetyRatings;
        }

        // toString, equals, and hashCode methods
        @Override
        public String toString() {
            return "Candidate{" +
                    "content=" + content +
                    ", finishReason='" + finishReason + '\'' +
                    ", index=" + index +
                    ", safetyRatings=" + safetyRatings +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Candidate candidate = (Candidate) o;
            return index == candidate.index &&
                   Objects.equals(content, candidate.content) &&
                   Objects.equals(finishReason, candidate.finishReason) &&
                   Objects.equals(safetyRatings, candidate.safetyRatings);
        }

        @Override
        public int hashCode() {
            return Objects.hash(content, finishReason, index, safetyRatings);
        }
    }

    public static class Content {
        private List<Part> parts;
        private String role;

        // Getters and setters
        public List<Part> getParts() {
            return parts;
        }

        public void setParts(List<Part> parts) {
            this.parts = parts;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        // toString, equals, and hashCode methods
        @Override
        public String toString() {
            return "Content{" +
                    "parts=" + parts +
                    ", role='" + role + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Content content = (Content) o;
            return Objects.equals(parts, content.parts) &&
                   Objects.equals(role, content.role);
        }

        @Override
        public int hashCode() {
            return Objects.hash(parts, role);
        }
    }

    public static class Part {
        private String text;

        // Getters and setters
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        // toString, equals, and hashCode methods
        @Override
        public String toString() {
            return "Part{" +
                    "text='" + text + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Part part = (Part) o;
            return Objects.equals(text, part.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text);
        }
    }

    public static class SafetyRating {
        private String category;
        private String probability;

        // Getters and setters
        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getProbability() {
            return probability;
        }

        public void setProbability(String probability) {
            this.probability = probability;
        }

        // toString, equals, and hashCode methods
        @Override
        public String toString() {
            return "SafetyRating{" +
                    "category='" + category + '\'' +
                    ", probability='" + probability + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SafetyRating that = (SafetyRating) o;
            return Objects.equals(category, that.category) &&
                   Objects.equals(probability, that.probability);
        }

        @Override
        public int hashCode() {
            return Objects.hash(category, probability);
        }
    }

    public static class PromptFeedback {
        private List<SafetyRating> safetyRatings;

        // Getters and setters
        public List<SafetyRating> getSafetyRatings() {
            return safetyRatings;
        }

        public void setSafetyRatings(List<SafetyRating> safetyRatings) {
            this.safetyRatings = safetyRatings;
        }

        // toString, equals, and hashCode methods
        @Override
        public String toString() {
            return "PromptFeedback{" +
                    "safetyRatings=" + safetyRatings +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PromptFeedback that = (PromptFeedback) o;
            return Objects.equals(safetyRatings, that.safetyRatings);
        }

        @Override
        public int hashCode() {
            return Objects.hash(safetyRatings);
        }
    }
}
