package com.uade.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.demo.repository.TokenRepository;


@Service
public class LogoutServiceImpl implements LogoutService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void logout(String token) {
        var storedToken = tokenRepository.findByJwtToken(token).orElse(null);
        if(storedToken != null){
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
    }
    
}
