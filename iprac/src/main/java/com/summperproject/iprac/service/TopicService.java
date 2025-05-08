package com.summperproject.iprac.service;

import java.util.List;
import java.util.Optional;

import com.summperproject.iprac.entity.Topic;

public interface TopicService {
    Topic saveTopic(Topic topic);
    Optional<Topic> getTopicById(Long id);
    List<Topic> getTopicsByLanguage(Long languageId);
    List<Topic> getTopicsByLanguageOrderedByIndex(Long languageId);
    Optional<Topic> getTopicByNameAndLanguage(String name, Long languageId);
    List<Topic> getAllTopics();
    void deleteTopic(Long id);
}
