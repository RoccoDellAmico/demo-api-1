package com.uade.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.demo.entity.Suggestion;
import com.uade.demo.entity.User;
import com.uade.demo.entity.dto.SuggestionDTO;
import com.uade.demo.repository.SuggestionRepository;
import com.uade.demo.repository.UserRepository;

@Transactional
@Service
public class SuggestionServiceImpl implements SuggestionService {

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public SuggestionDTO createSuggestion(String description, String photo, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Suggestion suggestion = suggestionRepository.save(new Suggestion(description, photo, user));
        return mapToSuggestionDTO(suggestion);
    }

    public List<SuggestionDTO> getSuggestions() {
        return suggestionRepository.findAll().stream().map(this::mapToSuggestionDTO).toList();
    }

    private SuggestionDTO mapToSuggestionDTO(Suggestion suggestion) {
        return new SuggestionDTO(
            suggestion.getId(), 
            suggestion.getDescription(), 
            suggestion.getPhoto(), 
            suggestion.getUser().getId(),
            suggestion.getUser().getEmail()
        );
    }

}
