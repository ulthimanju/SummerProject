package com.summperproject.iprac.model;

import java.util.List;
import java.util.Objects;

public class GeminiRequestDto {
    private List<Content> contents;

    // Getters and setters
    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    // toString, equals, and hashCode methods
    @Override
    public String toString() {
        return "GeminiRequestDto{" +
                "contents=" + contents +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeminiRequestDto that = (GeminiRequestDto) o;
        return Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contents);
    }

    public static class Content {
        private List<Part> parts;

        // Getters and setters
        public List<Part> getParts() {
            return parts;
        }

        public void setParts(List<Part> parts) {
            this.parts = parts;
        }

        // toString, equals, and hashCode methods
        @Override
        public String toString() {
            return "Content{" +
                    "parts=" + parts +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Content content = (Content) o;
            return Objects.equals(parts, content.parts);
        }

        @Override
        public int hashCode() {
            return Objects.hash(parts);
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
}
