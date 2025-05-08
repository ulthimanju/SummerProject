package com.summperproject.iprac.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.summperproject.iprac.entity.Language;
import com.summperproject.iprac.entity.Topic;
import com.summperproject.iprac.repository.LanguageRepository;
import com.summperproject.iprac.repository.TopicRepository;
import com.summperproject.iprac.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final LanguageRepository languageRepository;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, LanguageRepository languageRepository) {
        this.topicRepository = topicRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id);
    }

    @Override
    public List<Topic> getTopicsByLanguage(Long languageId) {
        Optional<Language> language = languageRepository.findById(languageId);
        return language.map(topicRepository::findByLanguage).orElse(Collections.emptyList());
    }

    @Override
    public List<Topic> getTopicsByLanguageOrderedByIndex(Long languageId) {
        Optional<Language> language = languageRepository.findById(languageId);
        return language.map(topicRepository::findByLanguageOrderByOrderIndexAsc).orElse(Collections.emptyList());
    }

    @Override
    public Optional<Topic> getTopicByNameAndLanguage(String name, Long languageId) {
        Optional<Language> language = languageRepository.findById(languageId);
        if (language.isPresent()) {
            return topicRepository.findByNameAndLanguage(name, language.get());
        }
        return Optional.empty();
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }
}
