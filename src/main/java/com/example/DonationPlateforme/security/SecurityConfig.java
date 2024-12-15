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
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
<<<<<<< HEAD
                .requestMatchers("/auth/login", "/auth/register", "/users", "/h2-console").permitAll() 
                .anyRequest().authenticated() 
=======
                .requestMatchers("/auth/login", "/auth/register","/users","/h2-console").permitAll() // Routes publiques
                .anyRequest().authenticated() // Routes protégées
>>>>>>> fca583781ab7494f668ed23d2afe646b2df85875
            )
            .formLogin(form -> form
                .loginPage("/auth/login") 
                .usernameParameter("email")
                .defaultSuccessUrl("/home", true)
                .loginProcessingUrl("/auth/login")
                .failureUrl("/auth/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout") 
                .logoutSuccessUrl("/auth/login?logout=true") 
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
