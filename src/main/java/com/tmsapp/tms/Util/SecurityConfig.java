package com.tmsapp.tms.Util;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

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
    
    
}