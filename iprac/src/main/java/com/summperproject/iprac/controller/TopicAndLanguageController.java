package com.summperproject.iprac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.summperproject.iprac.entity.Language;
import com.summperproject.iprac.entity.Topic;
import com.summperproject.iprac.service.LanguageService;
import com.summperproject.iprac.service.TopicService;

@RestController
@RequestMapping("/api")
public class TopicAndLanguageController {

    private final LanguageService languageService;
    private final TopicService topicService;

    @Autowired
    public TopicAndLanguageController(LanguageService languageService, TopicService topicService) {
        this.languageService = languageService;
        this.topicService = topicService;
    }

    // Language endpoints
    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getAllLanguages() {
        List<Language> languages = languageService.getAllLanguages();
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/languages/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable Long id) {
        return languageService.getLanguageById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/languages/name/{name}")
    public ResponseEntity<Language> getLanguageByName(@PathVariable String name) {
        return languageService.getLanguageByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/languages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Language> createLanguage(@RequestBody Language language) {
        Language createdLanguage = languageService.saveLanguage(language);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLanguage);
    }

    @PutMapping("/languages/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Language> updateLanguage(@PathVariable Long id, @RequestBody Language language) {
        return languageService.getLanguageById(id)
                .map(existingLanguage -> {
                    language.setId(id);
                    Language updatedLanguage = languageService.saveLanguage(language);
                    return ResponseEntity.ok(updatedLanguage);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/languages/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        if (languageService.getLanguageById(id).isPresent()) {
            languageService.deleteLanguage(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Topic endpoints
    @GetMapping("/topics")
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        return topicService.getTopicById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/languages/{languageId}/topics")
    public ResponseEntity<List<Topic>> getTopicsByLanguage(@PathVariable Long languageId) {
        List<Topic> topics = topicService.getTopicsByLanguage(languageId);
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/languages/{languageId}/topics/ordered")
    public ResponseEntity<List<Topic>> getTopicsByLanguageOrderedByIndex(@PathVariable Long languageId) {
        List<Topic> topics = topicService.getTopicsByLanguageOrderedByIndex(languageId);
        return ResponseEntity.ok(topics);
    }

    @PostMapping("/topics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        Topic createdTopic = topicService.saveTopic(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTopic);
    }

    @PutMapping("/topics/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic topic) {
        return topicService.getTopicById(id)
                .map(existingTopic -> {
                    topic.setId(id);
                    Topic updatedTopic = topicService.saveTopic(topic);
                    return ResponseEntity.ok(updatedTopic);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/topics/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        if (topicService.getTopicById(id).isPresent()) {
            topicService.deleteTopic(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
