package com.tmsapp.tms.Controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmsapp.tms.Service.AuthenticationService;
import com.tmsapp.tms.Service.Checkgroup;
import com.tmsapp.tms.Util.ApplicationConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private Checkgroup checkgroup;

    @PostMapping(path= "/authtoken/return/userinfo")
    public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody Map<String, Object> req, @CookieValue("authToken") String jwtToken) {
        Map<String, Object> result = new HashMap<>();
        System.out.println("testing\n");
        try {
            result = authenticationService.authenticateUser(req, jwtToken);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping(path= "/cg")
    public ResponseEntity<Map<String, Object>> checkGroup(@RequestBody Map<String, Object> req, @CookieValue("authToken") String jwtToken) {
        Map<String, Object> result = new HashMap<>();
        System.out.println("testing\n");

        String username = (String) req.get("un");
        String accGroup = (String) req.get("gn");
        try {
            boolean inGroup = checkgroup.checkgroup(username, accGroup);
            result.put("cgResult", inGroup);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }
}
