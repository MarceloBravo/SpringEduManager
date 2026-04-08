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
            .requestMatchers("/estudiantes/**").hasAnyRole(RolesEnum.ADMIN.name(), RolesEnum.USER.name())
            .requestMatchers("/cursos/**").hasAnyRole(RolesEnum.ADMIN.name(), RolesEnum.USER.name())
            .requestMatchers("/evaluaciones/**").hasAnyRole(RolesEnum.ADMIN.name(), RolesEnum.TEACHER.name())
            .requestMatchers("/api/**").permitAll()
            .anyRequest()
            .authenticated()
        )
        .csrf(csrf -> csrf.disable())
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/dashboard", true)
            .failureUrl("/login?error")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/logout?logout")
            .permitAll()
        );
        return http.build();
    }
}
