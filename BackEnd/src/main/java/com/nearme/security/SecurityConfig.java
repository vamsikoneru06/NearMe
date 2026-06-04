package com.nearme.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, ex) -> {
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("status", 401);
            body.put("error", "Unauthorized");
            body.put("message", "Authentication required - include a valid Bearer token");
            body.put("timestamp", Instant.now().toString());
            new ObjectMapper().writeValue(response.getOutputStream(), body);
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> {
            response.setStatus(403);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("status", 403);
            body.put("error", "Forbidden");
            body.put("message", "You do not have permission to perform this action");
            body.put("timestamp", Instant.now().toString());
            new ObjectMapper().writeValue(response.getOutputStream(), body);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF — stateless JWT API does not need it
            .csrf(csrf -> csrf.disable())

            // Stateless session — never store auth in HTTP session
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Allow H2 console frames (dev only)
            .headers(headers ->
                headers.frameOptions(frame -> frame.sameOrigin()))

            .authorizeHttpRequests(auth -> auth

                // ── Public ──────────────────────────────────────────────────
                .requestMatchers("/api/auth/**").permitAll()

                // All GET on existing category endpoints
                .requestMatchers(HttpMethod.GET,
                        "/api/movies/**",
                        "/api/restaurants/**",
                        "/api/shops/**",
                        "/api/activities/**",
                        "/api/events/**",
                        "/api/tourist-spots/**",
                        "/api/places/**").permitAll()

                // Location endpoints — GET public
                .requestMatchers(HttpMethod.GET, "/api/locations/**").permitAll()

                // Reviews — GET public; more-specific rules MUST come before /api/locations/**
                .requestMatchers(HttpMethod.GET,
                        "/api/locations/*/reviews").permitAll()

                // Swagger / OpenAPI
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**").permitAll()

                // H2 console (dev)
                .requestMatchers("/h2-console/**").permitAll()

                // ── Authenticated (any logged-in user) ───────────────────────
                // These MUST come before the broader ADMIN rules so Spring
                // applies the least-restrictive matching rule first.
                .requestMatchers("/api/favorites/**").authenticated()
                .requestMatchers(HttpMethod.POST,
                        "/api/locations/*/reviews").authenticated()
                .requestMatchers(HttpMethod.DELETE,
                        "/api/reviews/**").authenticated()

                // ── Admin-only writes ────────────────────────────────────────
                // Broad pattern — comes after the specific review/favorite rules
                .requestMatchers(HttpMethod.POST,
                        "/api/locations/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,
                        "/api/locations/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,
                        "/api/locations/**").hasRole("ADMIN")

                // Everything else requires login
                .anyRequest().authenticated()
            )

            // Register JWT filter before the default username/password filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

            // Return JSON on auth errors instead of Spring's default HTML pages
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .accessDeniedHandler(accessDeniedHandler()));

        return http.build();
    }
}
