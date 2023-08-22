package com.tmsapp.tms.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Repository.AccgroupRepository;
import com.tmsapp.tms.Repository.AccountRepository;
import com.tmsapp.tms.Util.ApplicationConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Service
public class AuthenticationService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccgroupRepository accgroupRepository;

    public Map<String, Object> authenticateUser(Map<String, Object> req, String jwtToken) {
        Map<String, Object> result = new HashMap<>();

        if(jwtToken == null) {
            result.put("success", false);
            result.put("message", "invalid token 31244");
            return result;
        }
        try {
            Jws<Claims> jwtContents = Jwts.parserBuilder()
                .setSigningKey(ApplicationConstant.SECURITY_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(jwtToken);

                //System.out.println(jwtContents);
                String username = jwtContents.getBody().getSubject();

                Account user =  accountRepository.getAccountByUsername(username);

                if(user == null) {
                    result.put("success", false);
                    result.put("message", "invalid token 3333");
                }
                List<Accgroup> userGroups  = accountRepository.getGroupsByUsername(username);

                //System.out.println(user.getUsername());
                // for (Accgroup userGroup : userGroups) {
                //     System.out.println(userGroup.getGroupName());
                // }
                List<String> userGroupNameList = new ArrayList<>();
                if(user!=null && !userGroups.isEmpty()) {
                    userGroups.forEach(userGroup -> userGroupNameList.add(userGroup.getGroupName()));
                }

                result.put("success", true);
                result.put("username", user.getUsername());
                result.put("groups", userGroupNameList);
                result.put("status", user.getStatus());
            
        } catch (Exception e) {
            // log exception e
            result.put("success", false);
            result.put("message", "invalid token ex");
            e.printStackTrace();
            return result;
        }
        return result;
    }
}
