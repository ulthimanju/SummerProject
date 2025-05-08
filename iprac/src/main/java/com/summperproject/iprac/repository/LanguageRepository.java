package com.summperproject.iprac.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.summperproject.iprac.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByName(String name);
    boolean existsByName(String name);
}
