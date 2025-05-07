package com.summperproject.iprac.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.summperproject.iprac.config.GeminiConfig;
import com.summperproject.iprac.model.GeminiRequestDto;
import com.summperproject.iprac.model.GeminiResponseDto;
import com.summperproject.iprac.model.GeminiRequestDto.Content;
import com.summperproject.iprac.model.GeminiRequestDto.Part;

@Service
public class GeminiService {
    
    private final RestTemplate restTemplate;
    private final GeminiConfig geminiConfig;
    
    public GeminiService(GeminiConfig geminiConfig) {
        this.geminiConfig = geminiConfig;
        this.restTemplate = new RestTemplate();
    }
    
    public GeminiResponseDto generateContent(String prompt) {
        GeminiRequestDto requestDto = createRequest(prompt);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        
        HttpEntity<GeminiRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);
        
        return restTemplate.postForObject(
            geminiConfig.getFullApiUrl(),
            requestEntity,
            GeminiResponseDto.class
        );
    }
    
    private GeminiRequestDto createRequest(String prompt) {
        GeminiRequestDto requestDto = new GeminiRequestDto();
        
        List<Content> contents = new ArrayList<>();
        Content content = new Content();
        
        List<Part> parts = new ArrayList<>();
        Part part = new Part();
        part.setText(prompt);
        parts.add(part);
        
        content.setParts(parts);
        contents.add(content);
        
        requestDto.setContents(contents);
        return requestDto;
    }
}