package com.summperproject.iprac.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.summperproject.iprac.entity.Language;
import com.summperproject.iprac.repository.LanguageRepository;
import com.summperproject.iprac.service.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Language saveLanguage(Language language) {
        return languageRepository.save(language);
    }

    @Override
    public Optional<Language> getLanguageById(Long id) {
        return languageRepository.findById(id);
    }

    @Override
    public Optional<Language> getLanguageByName(String name) {
        return languageRepository.findByName(name);
    }

    @Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public void deleteLanguage(Long id) {
        languageRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return languageRepository.existsByName(name);
    }
}
