package com.tmsapp.tms.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Repository.AccountRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    
    private CustomAuthenticationManager customAuthenticationManager;


    @Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

                try{
                    // try (InputStream inputStream = request.getInputStream();
                    //     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    //     String line;
                    //     while ((line = reader.readLine()) != null) {
                    //         System.out.println(line);
                    //     }
                    // }
                    // ObjectMapper objectMapper = new ObjectMapper();
                    // JsonNode jsonNode = objectMapper.readTree(request.getInputStream());
                    // System.out.println(jsonNode);
                    Account account = new ObjectMapper().readValue(request.getInputStream(), Account.class);
                    // System.out.println(account.getUsername());
                    Authentication authentication = new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());
                    return customAuthenticationManager.authenticate(authentication);
                }catch(IOException e){
                    e.printStackTrace();
                    throw new RuntimeException();
                }
    
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException{
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("Bad request, login failed");
        response.getWriter().flush();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException{
        String token = JWT.create()
                        .withSubject(authResult.getName())
                        .withClaim("ip", request.getRemoteAddr())
                        .withClaim("userAgent", request.getHeader("User-Agent"))
                        .withExpiresAt(new Date(System.currentTimeMillis() + ApplicationConstant.TOKEN_EXPIRE))
                        .sign(Algorithm.HMAC512(ApplicationConstant.SECURITY_KEY));

            
        Cookie jwtCookie = new Cookie("authToken", token);
        jwtCookie.setMaxAge(ApplicationConstant.TOKEN_EXPIRE);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        //Get user and return user in the body
        AccountRepository accountRepository = new AccountRepository(new HibernateUtil());
        Account account = accountRepository.getAccountByUsername(authResult.getName());
        account.setAccgroups(accountRepository.getGroupsByUsername(account.getUsername()));
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectJson = objectMapper.valueToTree(account);
        objectJson.put("success", true);
        String returnUserJson = objectMapper.writeValueAsString(objectJson);
        //System.out.println(returnUserJson);
        response.getWriter().write(returnUserJson);
    }
}
