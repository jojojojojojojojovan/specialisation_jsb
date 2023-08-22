package com.tmsapp.tms.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.AccgroupDTO;
import com.tmsapp.tms.Entity.Account;
import com.tmsapp.tms.Repository.AccgroupRepository;
import com.tmsapp.tms.Repository.AccountRepository;

@Service
public class AccgroupService {

    @Autowired
    AccgroupRepository accGroupRepository;

    @Autowired
    Checkgroup checkgroup;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    public AccgroupService(AccgroupRepository accGroupRepository) {
        this.accGroupRepository = accGroupRepository;
    }

    public Map<String, Object> createAccgroup(Map<String, Object> req) {
        Map<String, Object> result = new HashMap<>();
        Boolean user = checkgroup.checkgroup(req.get("un").toString(), req.get("gn").toString());
        Account account = accountRepository.getAccountByUsername(req.get("un").toString());
        try {
            if (user == true && account.getStatus() == 1) {
                Accgroup accgroup = null;
                if(req.get("groupName") != null  && !req.get("groupName").toString().isEmpty()){
                    accgroup = new Accgroup(req.get("groupName").toString());
                    boolean isCreated = accGroupRepository.createAccgroup(accgroup);
                    if(isCreated){
                        result.put("success", true);
                        return result;
                    }
                }
            }else{
                result.put("success", false);
                result.put("message", "status inactive");
                return result;
            }
        } catch (Exception error) {
            result.put("success", false);
            result.put("message", "status inactive");
            return result;
        }
        result.put("success", false);
        return result;
    }
    
    public Map<String, Object> getAllUserGroupDTO(Map<String, Object> req) {
        List<AccgroupDTO> dtoList = new ArrayList<>();
        boolean user = checkgroup.checkgroup(req.get("un").toString(), req.get("gn").toString());
        Map<String, Object> result = new HashMap<>();

        List<String> groupResult = new ArrayList<>();
        try {
            if (user) {
                List<Accgroup> accgroups = accGroupRepository.getAllAccGroup();
                groupResult = accgroups.stream().map(Accgroup::getGroupName).toList();

                if (accgroups != null) {
                    for (Accgroup accgroup : accgroups) {
                        dtoList.add(new AccgroupDTO(accgroup.getGroupName()));
                    }
                    result.put("success", true);
                    result.put("groups", groupResult);
                    return result;
                }
                
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
        result.put("success", false);
        return result;
    }

}
