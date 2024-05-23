package ar.edu.unnoba.pdyc.mymusic.security;

import jakarta.ws.rs.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private  UserDetailsService userDetailsService;
    @Autowired
    private  AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, AuthenticationConfiguration authenticationConfiguration) {
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
    }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .userDetailsService(userDetailsService)
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.GET,"/songs/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/playlists").permitAll()
                            .requestMatchers(HttpMethod.POST, "/playlists/**").authenticated()
                            .anyRequest().fullyAuthenticated())
                    .addFilter(new JWTAuthenticationFilter(
                            authenticationConfiguration.getAuthenticationManager()))
                    .addFilter(new JWTAuthorizationFilter(
                            authenticationConfiguration.getAuthenticationManager()));
            return http.build();
    }

        @Bean
        public UserDetailsService userDetailsService(){
            return this.userDetailsService;
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService);
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        }

}



