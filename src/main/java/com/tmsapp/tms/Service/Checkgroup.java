package com.tmsapp.tms.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Repository.AccountRepository;

@Service
public class Checkgroup {
    @Autowired
    AccountRepository accountRepository;

    public boolean checkgroup(String username, String groupName){
        System.out.println("check group was run");
        boolean result = false;
        Account account = accountRepository.getAccountByUsername(username);
        if(account != null){
            List<Accgroup> userGroups = accountRepository.getGroupsByUsername(account.getUsername());
            if(userGroups != null){
                for (Accgroup accgroup : userGroups) {
                if(groupName.equals(accgroup.getGroupName())){
                    result = true;
                }
            }
            }
        }

        return result;
    }
}
