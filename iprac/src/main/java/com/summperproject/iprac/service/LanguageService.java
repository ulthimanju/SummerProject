package com.summperproject.iprac.service;

import java.util.List;
import java.util.Optional;

import com.summperproject.iprac.entity.Language;

public interface LanguageService {
    Language saveLanguage(Language language);
    Optional<Language> getLanguageById(Long id);
    Optional<Language> getLanguageByName(String name);
    List<Language> getAllLanguages();
    void deleteLanguage(Long id);
    boolean existsByName(String name);
}
