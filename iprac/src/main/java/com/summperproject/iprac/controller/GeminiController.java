package com.summperproject.iprac.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.summperproject.iprac.model.GeminiResponseDto;
import com.summperproject.iprac.service.GeminiService;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {
    
    private final GeminiService geminiService;
    
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }
    
    @PostMapping("/generate")
    public ResponseEntity<GeminiResponseDto> generateContent(@RequestBody String prompt) {
        GeminiResponseDto response = geminiService.generateContent(prompt);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/explain")
    public ResponseEntity<String> explainTopic(@RequestBody String topic) {
        GeminiResponseDto response = geminiService.generateContent("Explain " + topic);
        
        // Extract the text from the response
        if (response.getCandidates() != null && !response.getCandidates().isEmpty() &&
            response.getCandidates().get(0).getContent() != null &&
            response.getCandidates().get(0).getContent().getParts() != null &&
            !response.getCandidates().get(0).getContent().getParts().isEmpty()) {
            
            String explanation = response.getCandidates().get(0)
                                        .getContent()
                                        .getParts()
                                        .get(0)
                                        .getText();
            
            return ResponseEntity.ok(explanation);
        }
        
        return ResponseEntity.ok("No explanation available");
    }
}