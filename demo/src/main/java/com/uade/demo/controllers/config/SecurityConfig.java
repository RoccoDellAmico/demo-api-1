package com.uade.demo.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;
        private final LogoutHandler logoutHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                /*List<String> allowedOrigins = new ArrayList<>();
                allowedOrigins.add("http://localhost:5173");

                List<String> allowedMethods = new ArrayList<>();
                allowedMethods.add("GET");
                allowedMethods.add("POST");
                allowedMethods.add("PUT");
                allowedMethods.add("DELETE");

                List<String> allowedHeaders = new ArrayList<>();
                allowedHeaders.add("Authorization");
                allowedHeaders.add("Content-Type");
                allowedHeaders.add("Accept");
                allowedHeaders.add("Origin");

                List<String> exposedHeaders = new ArrayList<>();
                exposedHeaders.add("Authorization");*/

                http
                                /*.cors().configurationSource(request -> {
                                        CorsConfiguration configuration = new CorsConfiguration();
                                        configuration.setAllowedOrigins(allowedOrigins); 
                        
                                        configuration.setAllowedMethods(allowedMethods); // Incluye OPTIONS para peticiones preflight
                                        configuration.setAllowedHeaders(allowedHeaders);// Puedes especificar los encabezados permitidos si lo deseas
                                        configuration.setAllowCredentials(true);
                                        configuration.setExposedHeaders(exposedHeaders); // Exponer encabezados personalizados si es necesario
                                        return configuration;
                                })
                                .and()*/
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests()
                                .requestMatchers(AppConstants.PUBLIC_URLS).permitAll()
                                .requestMatchers(AppConstants.USER_URLS).hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers(AppConstants.ADMIN_URLS).hasAuthority("ADMIN")
                                .anyRequest()
                                .authenticated()
                                .and()
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .logout()
                                .logoutUrl("/api/user/l0gout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(
                                        (request, response, authentication) -> 
                                        SecurityContextHolder.clearContext()
                                );
                return http.build();
        }
}
