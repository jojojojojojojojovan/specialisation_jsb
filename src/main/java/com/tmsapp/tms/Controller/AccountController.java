package com.tmsapp.tms.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody Account account) {
        Map<String, Object> result = new HashMap<>();

        //Call account service method
        result.putAll(accountService.createAccount(account));
        
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
