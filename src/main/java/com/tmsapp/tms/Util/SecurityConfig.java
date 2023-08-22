package com.tmsapp.tms.Util;


import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tmsapp.tms.Entity.JwtInvalidation;
import com.tmsapp.tms.Repository.JwtRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;




@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig{

    @Autowired
    CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    JwtRepository jwtRepository;

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    CustomLogoutHandler customLogoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/login");
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/accounts/*").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new ExceptionFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(jwtAuthorizationFilter, AuthenticationFilter.class)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(logout -> logout
                .addLogoutHandler(customLogoutHandler)
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) throws IOException, ServletException {

                            //Create a cookie with the same name and set the value to empty
                            Cookie deleteJwtCookie = new Cookie("authToken", "");

                            //Set the cookie max age to 0
                            deleteJwtCookie.setMaxAge(0);

                            //Override the cookie in the response
                            response.addCookie(deleteJwtCookie);
                    }
                }));

        return http.build();
    }
    
   @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3030")); // Specify your allowed origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Specify your allowed HTTP methods
        configuration.setAllowedHeaders(Arrays.asList("*")); // Specify your allowed headers
        configuration.setAllowCredentials(true); // Allow cookies
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply the configuration to all paths
        return source;
    }
    
        
}