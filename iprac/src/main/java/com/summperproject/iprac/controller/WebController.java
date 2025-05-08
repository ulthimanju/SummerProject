package com.summperproject.iprac.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.summperproject.iprac.entity.Exercise;
import com.summperproject.iprac.entity.Language;
import com.summperproject.iprac.entity.Topic;
import com.summperproject.iprac.service.ExerciseService;
import com.summperproject.iprac.service.LanguageService;
import com.summperproject.iprac.service.TopicService;

@Controller
public class WebController {

    private final LanguageService languageService;
    private final TopicService topicService;
    private final ExerciseService exerciseService;

    @Autowired
    public WebController(LanguageService languageService,
                        TopicService topicService,
                        ExerciseService exerciseService) {
        this.languageService = languageService;
        this.topicService = topicService;
        this.exerciseService = exerciseService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Language> languages = languageService.getAllLanguages();
        model.addAttribute("languages", languages);
        return "index";
    }

    @GetMapping("/languages")
    public String languages(Model model) {
        List<Language> languages = languageService.getAllLanguages();
        model.addAttribute("languages", languages);
        return "languages";
    }

    @GetMapping("/languages/{id}")
    public String languageDetail(@PathVariable Long id, Model model) {
        Optional<Language> language = languageService.getLanguageById(id);
        if (language.isPresent()) {
            model.addAttribute("language", language.get());

            List<Topic> topics = topicService.getTopicsByLanguageOrderedByIndex(id);
            model.addAttribute("topics", topics);

            return "language-detail";
        }
        return "redirect:/languages";
    }

    @GetMapping("/topics/{id}")
    public String topicDetail(@PathVariable Long id, Model model) {
        Optional<Topic> topic = topicService.getTopicById(id);
        if (topic.isPresent()) {
            model.addAttribute("topic", topic.get());

            List<Exercise> exercises = exerciseService.getExercisesByTopicOrderedByDifficulty(id);
            model.addAttribute("exercises", exercises);

            return "topic-detail";
        }
        return "redirect:/languages";
    }

    @GetMapping("/exercises/{id}")
    public String exerciseDetail(@PathVariable Long id, Model model) {
        Optional<Exercise> exercise = exerciseService.getExerciseById(id);
        if (exercise.isPresent()) {
            model.addAttribute("exercise", exercise.get());
            return "exercise-detail";
        }
        return "redirect:/languages";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/gemini")
    public String gemini() {
        return "gemini";
    }
}
