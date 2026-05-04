package com.example.projet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.projet.config.CorsConfig;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    @Lazy
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
            // 1. Désactiver CSRF (API stateless JWT)
            .csrf(csrf -> csrf.disable())

            // 2. Pas de session HTTP (JWT gère l'auth)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 3. Règles d'autorisation par endpoint
            .authorizeHttpRequests(auth -> auth

                // OPTIONS preflight (CORS)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Swagger
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                // Auth publique
                .requestMatchers("/api/auth/**").permitAll()
               

                // Lecture publique 
                .requestMatchers(HttpMethod.GET,
                "/api/clinics/**",
                "/api/doctors/**",
                "/api/specialties/**",
                "/api/reviews/clinic/**",
                "/api/quote-requests/token/**",
                "/api/quote-responses/token/**"
                ).permitAll()

                // USERS (ADMIN ONLY)
                .requestMatchers(HttpMethod.POST, "/api/users/admins").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/users/clinic-admins").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                // CLINICS
                .requestMatchers(HttpMethod.POST, "/api/clinics").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/clinics/**").hasAnyRole("ADMIN", "CLINIC_ADMIN")

                // Specialty association — permit authenticated, @PreAuthorize gère le rôle
                .requestMatchers(HttpMethod.POST, "/api/clinics/*/specialties/*").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/clinics/*/specialties/*").authenticated()

                // Delete clinique seulement — ADMIN
                .requestMatchers(HttpMethod.DELETE, "/api/clinics/**").authenticated()

                // DOCTORS
                .requestMatchers(HttpMethod.POST, "/api/doctors").hasRole("CLINIC_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/doctors/**").hasRole("CLINIC_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/doctors/**").hasRole("CLINIC_ADMIN")

                // SPECIALTIES
                .requestMatchers(HttpMethod.POST, "/api/specialties").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/specialties/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/specialties/**").hasRole("ADMIN")

                // REVIEWS
                .requestMatchers(HttpMethod.POST, "/api/reviews").permitAll() 
                .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("ADMIN")

                // QUOTE REQUEST (PATIENT)
                .requestMatchers(HttpMethod.POST, "/api/quote-requests").permitAll() // public (client externe)
                .requestMatchers(HttpMethod.GET, "/api/quote-requests/**").hasAnyRole("ADMIN", "CLINIC_ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/quote-requests/**").hasAnyRole("ADMIN", "CLINIC_ADMIN")

                // QUOTE RESPONSE (CLINIC)
                .requestMatchers(HttpMethod.POST, "/api/quote-responses").hasRole("CLINIC_ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/quote-responses/*/view").hasRole("CLINIC_ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/quote-responses/*/accept").hasRole("CLINIC_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/quote-responses/**").hasAnyRole("CLINIC_ADMIN", "ADMIN")                


                // Tout le reste → authentifié
                .anyRequest().authenticated()
                //.anyRequest().permitAll()
                )

            // 4. Ajouter le filtre JWT
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}