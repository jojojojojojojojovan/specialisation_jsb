package com.tmsapp.tms.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.tmsapp.tms.Entity.JwtInvalidation;
import com.tmsapp.tms.Repository.JwtRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class CustomLogoutHandler implements LogoutHandler{
    
    private JwtRepository jwtRepository;

    @Autowired
    public CustomLogoutHandler (JwtRepository jwtRepository){
        this.jwtRepository = jwtRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        //Add JWT into the db
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if("authToken".equals(cookie.getName())){
                JwtInvalidation jwt = new JwtInvalidation(cookie.getValue());
                System.out.println(jwt);
                boolean jwtResult = jwtRepository.InvalidateJWT(jwt);
            }
        }
        
        //Clearing Secuirty Context Holder
        SecurityContextHolder.clearContext();
    }
    
}
