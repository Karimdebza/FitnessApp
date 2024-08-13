package com.fitnessapp.Security;

import com.fitnessapp.Security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity


public class SecurityConfig  {

private final JwtRequestFilter jwtRequestFilter;
private final UserDetailsService userDetailsService;

public SecurityConfig (JwtRequestFilter jwtRequestFilter, UserDetailsService userDetailsService){
    this.jwtRequestFilter = jwtRequestFilter;
    this.userDetailsService = userDetailsService;
}

@Bean
    public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}
@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
}
@Bean public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http.csrf(csrf ->  csrf.disable()) // Désactive CSRF, version mise à jour
            .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/authenticate").permitAll()// Autorise l'accès à l'endpoint d'authentification
            .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
    )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
    http.addFilterBefore(jwtRequestFilter, usernamePasswordAuthentificationFilter.class);
    return http.build();



}

}
