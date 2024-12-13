package com.example.DonationPlateforme.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Désactive la protection CSRF pour simplifier
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/login", "/auth/register", "/users", "/annonces","/categories","/products","/order-lots","/h2-console").permitAll() // Routes publiques
                .anyRequest().authenticated() // Routes protégées
            )
            .formLogin(form -> form
                .loginPage("/auth/login") // Chemin de la page de connexion
                .usernameParameter("email")
                .defaultSuccessUrl("/auth/success", true)
                .loginProcessingUrl("/auth/login")
                .failureUrl("/auth/login?error=true") // Redirection en cas d'échec
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout") // déconnexion
                .logoutSuccessUrl("/auth/login?logout=true") // Redirection après déconnexion
                .permitAll()
            );

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
