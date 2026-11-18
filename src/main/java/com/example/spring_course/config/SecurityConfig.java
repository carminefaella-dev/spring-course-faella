package com.example.spring_course.config;

import com.example.spring_course.repository.UserAccountRepository;
import com.example.spring_course.repository.model.UserAccount;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/register").permitAll()
                .requestMatchers("/api/catalog/products/public/**").permitAll()
                .requestMatchers("/api/catalog/products/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/catalog/products/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            ).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner createAdmin(UserAccountRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!repository.existsByUsername("admin")) {
                UserAccount admin = new UserAccount(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        "ADMIN"
                );

                repository.save(admin);
            }
        };
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.builder()
//                .username("mario")
//                .password(passwordEncoder.encode("password123"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}