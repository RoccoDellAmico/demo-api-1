package com.uade.demo.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.demo.controllers.YourCustomException;
import com.uade.demo.controllers.auth.AuthenticationRequest;
import com.uade.demo.controllers.auth.AuthenticationResponse;
import com.uade.demo.controllers.auth.RegisterRequest;
import com.uade.demo.controllers.config.JwtService;
import com.uade.demo.entity.Role;
import com.uade.demo.entity.User;
import com.uade.demo.exceptions.UserDuplicateException;
import com.uade.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {
                //throws UserDuplicateException {
                /*var user = User.builder()
                                .firstName(request.getFirstname())
                                .lastName(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(request.getRole())
                                .build();*/

                Optional<User> usuario = repository.findByEmail(request.getEmail());
                if (usuario.isPresent())
                        throw new YourCustomException("ERROR! El usuario ya existe");
                
                if (!emailValidation(request.getEmail()))
                        throw new YourCustomException("ERROR! Email invalido");

                User user = new User(
                                request.getEmail(), 
                                passwordEncoder.encode(request.getPassword()),
                                request.getEmail(),
                                request.getFirstname(),
                                request.getLastname(),
                                Role.USER);

                repository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));
                var user = repository.findByEmail(request.getEmail())
                                .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }

        private static boolean emailValidation(String email) {
                String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                return matcher.matches();
        }
}
