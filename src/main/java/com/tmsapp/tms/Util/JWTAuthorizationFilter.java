package com.tmsapp.tms.Util;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tmsapp.tms.Entity.JwtInvalidation;
import com.tmsapp.tms.Repository.JwtRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter{

    
    private JwtRepository jwtRepository;

    @Autowired
    public JWTAuthorizationFilter(JwtRepository jwtRepository){
        this.jwtRepository = jwtRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("DEF");
        String jwt = null;
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            filterChain.doFilter(request, response);
            return;
        }
        for (Cookie cookie : cookies) {
            if("authToken".equals(cookie.getName())){
                jwt = cookie.getValue();
            }
        }

        if(jwt == null){
            filterChain.doFilter(request, response);
            return;
        }

        //Validate against JwtInvalidation 
        JwtInvalidation invalidJWT = jwtRepository.checkInvalidateJwt(jwt);
        if(invalidJWT != null){
            filterChain.doFilter(request, response);
            return;
        }

        //Check user-agent & ipAddress
        String currentUserIpAddress = request.getRemoteAddr();
        String currentUserAgent = request.getHeader("User-Agent");
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(ApplicationConstant.SECURITY_KEY)).build();
        DecodedJWT decodedJWT = verifier.verify(jwt);
        if(!decodedJWT.getClaim("ip").asString().equals(currentUserIpAddress) || !decodedJWT.getClaim("userAgent").asString().equals(currentUserAgent)){
            filterChain.doFilter(request, response);
            return;
        }

        String user = JWT.require(Algorithm.HMAC512(ApplicationConstant.SECURITY_KEY))
                    .build()
                    .verify(jwt)
                    .getSubject();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
    
}
