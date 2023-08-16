package com.tmsapp.tms.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Service.AccountService;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    AccountService accountService;

    //INPUT: username, password, email(OPTIONAL), status, LIST<accgroup>
    @PostMapping(path = "/create")
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody Map<String, Object> req) {
        Map<String, Object> result = new HashMap<>();

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
    @PostMapping(path = "/getAllAccounts")
    public ResponseEntity<Map<String, Object>> getAllAccounts(@RequestBody Map<String, Object> req) {
        Map<String, Object> result = new HashMap<>();
        result.putAll(accountService.getAllAccounts(req));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
    //INPUT: username, newPassword(Optional), email(Optional), verifyPassword
    @PostMapping(path = "/updateAccount")
    public ResponseEntity<Map<String, Object>> updateAccount(@RequestBody Map<String, Object> req){
        Map<String, Object> result = new HashMap<>();
        result.putAll(accountService.updateAccount(req));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
