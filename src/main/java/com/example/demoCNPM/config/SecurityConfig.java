package com.example.demoCNPM.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final UserDetailsService userDetailsService;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth

                            // public
                            .requestMatchers("/login", "/css/**", "/employee/**").permitAll()

                            // role
                            .requestMatchers("/reception/**").hasAnyRole("RECEPTION", "TECHNICAL")
                            .requestMatchers("/technical/**").hasRole("TECHNICAL")

                            .anyRequest().authenticated()
                    )

                    .formLogin(form -> form
                            .loginPage("/login")
                            .loginProcessingUrl("/do-login")
                            .defaultSuccessUrl("/home", true)
                            .permitAll()
                    )

                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout")
                    );

            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance();
        }
    }
