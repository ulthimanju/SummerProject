package com.summperproject.iprac.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.summperproject.iprac.entity.Language;
import com.summperproject.iprac.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByLanguage(Language language);
    List<Topic> findByLanguageOrderByOrderIndexAsc(Language language);
    Optional<Topic> findByNameAndLanguage(String name, Language language);
}
