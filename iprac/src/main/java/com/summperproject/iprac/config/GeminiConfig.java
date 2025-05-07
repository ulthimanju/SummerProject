package com.summperproject.iprac.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {
    
    @Value("${gemini.api.key:AIzaSyA2uhq-oUgJWf_6_mj9BV-eD8H_6vZoYuI}")
    private String apiKey;
    
    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent}")
    private String apiUrl;
    
    public String getApiKey() {
        return apiKey;
    }
    
    public String getApiUrl() {
        return apiUrl;
    }
    
    public String getFullApiUrl() {
        return apiUrl + "?key=" + apiKey;
    }
}