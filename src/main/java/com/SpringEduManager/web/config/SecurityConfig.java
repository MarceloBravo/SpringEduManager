package com.SpringEduManager.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.SpringEduManager.web.enums.RolesEnum;

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
            .requestMatchers("/login","/register","/css/**","/js/**","/api/users")
            .permitAll()
            .requestMatchers("/users/**").hasAnyRole(RolesEnum.ADMIN.name())
            .requestMatchers("/students/**").hasAnyRole(RolesEnum.ADMIN.name())
            .requestMatchers("/course/**").hasAnyRole(RolesEnum.ADMIN.name())
            .requestMatchers("/evaluations/**").hasAnyRole(RolesEnum.ADMIN.name())
            .requestMatchers("/api/**").permitAll()
            .anyRequest()
            .authenticated()
        )
        .csrf(csrf -> csrf.disable())
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/dashboard", true)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/logout?logout")
            .permitAll()
        );
        return http.build();
    }
}
