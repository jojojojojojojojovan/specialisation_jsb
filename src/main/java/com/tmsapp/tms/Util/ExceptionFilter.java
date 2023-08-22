package com.tmsapp.tms.Util;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }
        catch(TokenExpiredException e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Exception filter: token expired");
            response.getWriter().flush();
        }
        catch(EntityNotFoundException e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Exception filter: entity not found");
            response.getWriter().flush();
        }
        catch(RuntimeException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Exception filter: bad request");
            response.getWriter().flush();
        }
    }
    
}
