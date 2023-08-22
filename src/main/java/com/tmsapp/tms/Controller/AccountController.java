package com.tmsapp.tms.Controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmsapp.tms.Entity.AccgroupDTO;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Service.AccountService;
import com.tmsapp.tms.Service.Checkgroup;
import com.tmsapp.tms.Util.ApplicationConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    Checkgroup checkgroup;

    //INPUT: username, password, email(OPTIONAL), status, LIST<accgroup>
    @PostMapping(path = "/create")
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody Map<String, Object> req, @CookieValue("authToken") String jwtToken) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
        Jws<Claims> jwtContents = Jwts.parserBuilder()
            .setSigningKey(ApplicationConstant.SECURITY_KEY.getBytes(StandardCharsets.UTF_8))
            .build()
            .parseClaimsJws(jwtToken);

            System.out.println(jwtContents);
            String username = jwtContents.getBody().getSubject();
            System.out.println("username: " + username);

            boolean authorized = checkgroup.checkgroup(username, "admin");
            System.out.println(authorized);
            if(!authorized) {
                response.put("success", false);
                response.put("message", "User does not have permission");
                return ResponseEntity.ok(response);
            }
        }
        catch(Exception e) {
            response.put("success", false);
            response.put("message", "User does not have permission");
            return ResponseEntity.ok(response);
        }

        Map<String, Object> result = new HashMap<>();

        System.out.println(req.get("account"));

        //Call account service method
        result.putAll(accountService.createAccount(req));
        
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //INPUT: username, password, email(OPTIONAL), status, LIST<accgroup>
    @PutMapping(path = "/admin/update")
    public ResponseEntity<Map<String, Object>> adminUpdateAccount(@RequestBody Map<String, Object> req) {
        Map<String, Object> result = new HashMap<>();
        //Call account service method
        result = accountService.adminUpdateAccount(req);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //INPUT: username
    @PostMapping(path = "/getUserProfile")
    public ResponseEntity<Map<String, Object>> getUserProfile(@RequestBody Map<String, Object> req) {
        Map<String, Object> result = accountService.getUserProfile(req);
        
        return ResponseEntity.ok(result);
    }

    //INPUT: username, un, gn="admin"
    @PostMapping(path = "/admin/getUserProfile")
    public ResponseEntity<Map<String, Object>> adminGetUserProfile(@RequestBody Map<String, Object> req) {
        Map<String, Object> result = accountService.adminGetUserProfile(req);
        
        return ResponseEntity.ok(result);
    }

    //INPUT: username
    @PostMapping(path = "/getAllAccounts")
    public ResponseEntity<Map<String, Object>> getAllAccounts(@RequestBody Map<String, Object> req) {
        Map<String, Object> result = new HashMap<>();
        result.putAll(accountService.getAllAccounts(req));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
    //INPUT: username, newPassword(Optional), email(Optional), verifyPassword
    @PutMapping(path = "/updateAccount")
    public ResponseEntity<Map<String, Object>> updateAccount(@RequestBody Map<String, Object> req){
        Map<String, Object> result = new HashMap<>();
        result.putAll(accountService.updateAccount(req));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
