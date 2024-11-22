package com.uade.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.demo.entity.dto.CreateSuggestionRequest;
import com.uade.demo.entity.dto.SuggestionDTO;
import com.uade.demo.service.SuggestionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class SuggestionController {
    @Autowired
    private SuggestionService suggestionService;

    @PostMapping("/user/suggestion/create")
    public ResponseEntity<SuggestionDTO> createSuggestion(@RequestBody CreateSuggestionRequest createSuggestionRequest) {
        SuggestionDTO suggestionDTO = suggestionService.createSuggestion(createSuggestionRequest.getDescription(), 
            createSuggestionRequest.getPhoto(), createSuggestionRequest.getUserId());
        return ResponseEntity.ok(suggestionDTO);
    }
    
    @GetMapping("/admin/suggestions")
    public ResponseEntity<List<SuggestionDTO>> getSuggestions() {
        return ResponseEntity.ok(suggestionService.getSuggestions());
    }
}
