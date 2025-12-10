package com.scholartrack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.config.http.SessionCreationPolicy; 
import org.springframework.security.authentication.AuthenticationProvider; 
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; 
import org.springframework.security.core.userdetails.UserDetailsService;
import com.scholartrack.config.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())
            // Set session management to stateless (API only)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Define Authorization Rules
			.authorizeHttpRequests(auth -> auth
                // Public endpoints
				.requestMatchers("/api/auth/**", "/api/health").permitAll()
                // ADMIN access for user, teacher, location creation/management
                .requestMatchers("/api/users/**", "/api/teachers/**", "/api/locations/**", "/api/students").hasRole("ADMIN")
                // Teacher/Admin access to manage courses, assignments, attendance, performance, and enrollment
                .requestMatchers("/api/courses/**", "/api/assignments/**", "/api/attendance/**", "/api/performances/**", "/api/enrollments/**").hasAnyRole("ADMIN", "TEACHER")
                // Student can only access their own reports and certain read-only endpoints
                .requestMatchers("/api/analytics/student/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                // All other API requests must be authenticated
				.anyRequest().authenticated()
			)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable) // Disable basic auth
            
            // Set up authentication provider and JWT filter
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}