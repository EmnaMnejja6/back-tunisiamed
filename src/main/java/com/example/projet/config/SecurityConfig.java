package com.example.projet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // public
                .requestMatchers(HttpMethod.GET, "/api/clinics/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/specialty-types").permitAll()

                // ADMIN seulement
                .requestMatchers(HttpMethod.POST, "/api/clinics").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/clinics/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/specialty-types").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/payments/admin/all").hasRole("ADMIN")

                // CLINIC_ADMIN seulement*/
                //.requestMatchers(HttpMethod.POST, "/api/clinics/*/doctors").hasRole("CLINIC_ADMIN")
                //.requestMatchers(HttpMethod.PUT, "/api/clinics/*/doctors/*").hasRole("CLINIC_ADMIN")
                //.requestMatchers(HttpMethod.DELETE, "/api/clinics/*/doctors/*").hasRole("CLINIC_ADMIN")
                //.requestMatchers(HttpMethod.POST, "/api/clinics/*/specialties").hasRole("CLINIC_ADMIN")
                //.requestMatchers(HttpMethod.GET, "/api/payments/clinic").hasRole("CLINIC_ADMIN")
                //.requestMatchers(HttpMethod.GET, "/api/appointments/clinic").hasRole("CLINIC_ADMIN")
                //.requestMatchers(HttpMethod.PATCH, "/api/appointments/*/complete").hasRole("CLINIC_ADMIN")

                // PATIENT seulement
                //.requestMatchers(HttpMethod.POST, "/api/appointments").hasRole("PATIENT")
                //.requestMatchers(HttpMethod.GET, "/api/appointments/my").hasRole("PATIENT")
                //.requestMatchers(HttpMethod.POST, "/api/payments/simulate/*").hasRole("PATIENT")
                //.requestMatchers(HttpMethod.GET, "/api/payments/my").hasRole("PATIENT")

                // PATIENT + CLINIC_ADMIN
                //.requestMatchers(HttpMethod.PATCH, "/api/appointments/*/cancel")
                    //.hasAnyRole("PATIENT", "CLINIC_ADMIN")

                // tout le reste nécessite authentification
                //.anyRequest().authenticated()
            //);
        //return http.build();
    //}
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
        return http.build();
    }
}