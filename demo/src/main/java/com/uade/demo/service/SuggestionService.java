package com.uade.demo.service;

import java.util.List;
import com.uade.demo.entity.dto.SuggestionDTO;

public interface SuggestionService {
    SuggestionDTO createSuggestion(String description, String photo, Long userId);
    List<SuggestionDTO> getSuggestions();
}
