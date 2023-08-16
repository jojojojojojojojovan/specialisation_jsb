package com.tmsapp.tms.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Entity.JwtInvalidation;
import com.tmsapp.tms.Repository.AccountRepository;
import com.tmsapp.tms.Repository.JwtRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtRepository jwtRepository;


    public Map<String, Object> createAccount(Account account){
        Map<String, Object> result = new HashMap<>(); 

        //Check all fields
        if(account.getUsername() == null || account.getPassword() == null){
            result.put("success", false);
            result.put("message", "Required fields missing");
            return result;
        }

        //Password regex 
        String passwordRegex = "^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,10}$";
        
        if(account.getPassword().matches(passwordRegex)){
            //Bcrypt password
            account.setPassword(passwordEncoder.encode(account.getPassword()));

            //Create account
            Boolean isCreated = accountRepository.createAccount(account) || false;

            if(isCreated){
                result.put("success", true);
            }
            else{
                result.put("success", false);
            }
        }
        else{
            result.put("success", false);
        }

        return result;
    }

    // public Map<String, Object> login(Map<String, Object> req){
    //     Map<String, Object> result = new HashMap<>();

    //     //Check if username and password is in the request
    //     if(req.get("username").toString() == null && req.get("password").toString() == null){
    //         result.put("success", false);
    //         return result;
    //     }

    //     result.put("success", true);
    //     return result;
    // } 

    // public Map<String, Object> logout(HttpServletResponse response, HttpServletRequest request){
    //     Map<String, Object> result = new HashMap<>();

    //     //Add JWT into the db
    //     Cookie[] cookies = request.getCookies();
    //     for (Cookie cookie : cookies) {
    //         if("authToken".equals(cookie.getValue())){
    //             JwtInvalidation jwt = new JwtInvalidation(cookie.getValue());
    //             boolean jwtResult = jwtRepository.InvalidateJWT(jwt);
    //             if(jwtResult){
    //                 result.put("success", true);
    //             }else{
    //                 result.put("success", false);
    //             }
    //         }
    //     }

    //     return result;

    // }
}