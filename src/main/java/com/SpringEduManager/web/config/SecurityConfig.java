package com.SpringEduManager.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico")
            .permitAll()
            .requestMatchers("/login", "/register", "/api/users")
            .permitAll()
            .requestMatchers("/estudiantes/**").hasAnyRole("ADMIN", "USER")
            .requestMatchers("/users/**").hasAnyRole("ADMIN")
            .requestMatchers("/cursos/**").hasAnyRole("ADMIN", "USER")
            .requestMatchers("/evaluaciones/**").hasAnyRole("ADMIN", "TEACHER")
            .requestMatchers("/api/**").permitAll()
            .anyRequest()
            .authenticated()
        )
        .csrf(csrf -> csrf.disable())
        .formLogin(form -> form
            //.loginPage("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .defaultSuccessUrl("/home", true)
            //.failureUrl("/login?error")
            .permitAll()
        )
        .logout(logout -> logout
            //.logoutSuccessUrl("/login?logout")
            .permitAll()
        );
        return http.build();
    }
}
