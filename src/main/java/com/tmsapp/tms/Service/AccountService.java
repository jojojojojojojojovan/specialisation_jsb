package com.tmsapp.tms.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Entity.AccountDTO;
import com.tmsapp.tms.Entity.JwtInvalidation;
import com.tmsapp.tms.Repository.AccountRepository;
import com.tmsapp.tms.Repository.JwtRepository;
import com.tmsapp.tms.Util.ApplicationConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtRepository jwtRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Checkgroup checkgroup;


    public Map<String, Object> createAccount(Map<String, Object> req){
        Map<String, Object> result = new HashMap<>(); 
        
        //Create account
        try {
            Account account = objectMapper.readValue(objectMapper.writeValueAsString(req.get("account")), Account.class);
            //Check all fields
            if(account.getUsername() == null || account.getPassword() == null){
                result.put("success", false);
                result.put("message", "Required fields missing");
                return result;
            }

            //Password regex 
            String passwordRegex = "^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,10}$";
            String emailPattern = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";

            //Email regex
            if(account.getEmail() == null || !account.getEmail().matches(emailPattern)){
                result.put("success",false);
                return result;
            }

            //Password regex
            if(!account.getPassword().toString().matches(passwordRegex)){
                result.put("success", false);
                return result;
            }
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

            return result;

        } catch (JsonMappingException e) {
            e.printStackTrace();
            result.put("success", false);
            return result;
        } catch (JsonProcessingException e) {

            e.printStackTrace();
            result.put("success", false);
            return result;
        }
        

    }

    public Map<String, Object> adminUpdateAccount(Map<String, Object> req) {
        Map<String, Object> res = new HashMap<>();
        System.out.println("this was ran");
        System.out.println(req.get("un"));
        System.out.println(req.get("gn"));
        try {
            boolean checkGroup = checkgroup.checkgroup((String) req.get("un"), (String) req.get("gn"));
            System.out.println(checkGroup);
            if(!checkGroup) {
                res.put("success", false);
                res.put("message", "unauthorized");
                return res;
            }
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
        
        //validate email and password
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,10}$";
        String emailRegex = "^\\S+@\\S+\\.\\S+$";
        
        Account newAccount = new Account();
        try {
            //converts java Object to json object to be converted to Account entity
            newAccount = objectMapper.readValue(objectMapper.writeValueAsString(req.get("account")), Account.class);
            String password = newAccount.getPassword();
            String email = newAccount.getEmail();

            System.out.println(password + email);
            if(password != null) {
                System.out.println(password);
                if(!Pattern.matches(passwordRegex, password)) {
                    res.put("success", false);
                    res.put("message", "invalid password");
                    return res;
                } else {
                    newAccount.setPassword(passwordEncoder.encode(newAccount.getPassword()));
                }
            }
            if(email != null) {
                if(!Pattern.matches(emailRegex, email)) {
                    res.put("success", false);
                    res.put("message", "invalid email");
                return res;
                }
            }
            
            
            int status = newAccount.getStatus();
            if(status != 0 && status != 1) {
                res.put("success", false);
                res.put("message", "invalid status");
                return res;
            }
            accountRepository.updateAccount(newAccount);
        }
        catch(Exception e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("message", "unable to update user");
            return res;
        }
        res.put("success", true);
        return res;
    }

    public Map<String, Object> getUserProfile(Map<String, Object> req){
        Map<String, Object> res = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String username = req.get("un").toString();
        // if(jwtToken == null) {
        //     res.put("success", false);
        //     res.put("message", "invalid token");
        //     return res;
        // }
        try{
            //use token get username
            // Jws<Claims> jwtContents = Jwts.parserBuilder()
            //     .setSigningKey(ApplicationConstant.SECURITY_KEY.getBytes(StandardCharsets.UTF_8))
            //     .build()
            //     .parseClaimsJws(jwtToken);
            // String username = jwtContents.getBody().getSubject();
            Account account = accountRepository.getAccountByUsername(username);
            res = objectMapper.convertValue(account, Map.class);
            res.remove("status");
            res.put("success",true);
            System.out.println(res);
            return res;
        } catch (Exception e){
            System.err.println(e);
            res.put("success",false);
        }
        return res;
    }
    
    public Map<String, Object> adminGetUserProfile(Map<String, Object> req){
        Map<String, Object> res = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
    
        try{
            //use token get username
            Account editedAccount = accountRepository.getAccountByUsername(req.get("username").toString());
            
            boolean isadmin = checkgroup.checkgroup(req.get("un").toString(), req.get("gn").toString());
            if (isadmin){
                res = objectMapper.convertValue(editedAccount, Map.class);
                res.put("success",true);
                System.out.println(res);
            }
            else{
                res.put("success",false);
                res.put("message","User is not authorised");
            }
            return res;
        } catch (Exception e){
            System.err.println(e);
            res.put("success",false);
        }
        return res;
    }



    // public Map<String, Object> login(Map<String, Object> req){
    //     Map<String, Object> result = new HashMap<>();
    public Map<String, Object> getAllAccounts(Map<String, Object> req){
        Map<String, Object> result = new HashMap<>();
        boolean isAdmin = checkgroup.checkgroup(req.get("un").toString(), req.get("gn").toString());
        if(!isAdmin) {
            result.put("Success", false);
            return result;
        }
        List<AccountDTO> accountList = accountRepository.getAllAccounts();
        result.put("success", true);
        result.put("accounts", accountList);
        return result;
    }

    public Map<String, Object> updateAccount(Map<String, Object> req){
        Map<String, Object> result = new HashMap<>();
        //Get account 
        Account account = accountRepository.getAccountByUsername(req.get("username").toString());
        if(account == null){
            result.put("success", false);
            return result;
        }

        //Authenticate 
        if (!passwordEncoder.matches(req.get("verifyPassword").toString(), account.getPassword())){
            result.put("success", false);
            return result;
        }

        //Update account 
        if(req.get("email")!= null){
            account.setEmail(req.get("email").toString());
        }
        if(req.get("newPassword") != null){
            account.setPassword(passwordEncoder.encode(req.get("newPassword").toString()));
        }
        
        boolean isUpdated = accountRepository.updateAccount(account);
        result.put("success", isUpdated);
        return result;
    }

}