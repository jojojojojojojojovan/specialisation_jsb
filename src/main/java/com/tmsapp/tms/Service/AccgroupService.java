package com.tmsapp.tms.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.AccgroupDTO;
import com.tmsapp.tms.Repository.AccgroupRepository;

@Service
public class AccgroupService {

    @Autowired
    AccgroupRepository accGroupRepository;

    @Autowired
    Checkgroup checkgroup;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public AccgroupService(AccgroupRepository accGroupRepository) {
        this.accGroupRepository = accGroupRepository;
    }

    public Map<String, Object> createAccgroup(Map<String, Object> req) {
        Map<String, Object> result = new HashMap<>();
        Boolean createAgResult = false;
        Boolean user = checkgroup.checkgroup(req.get("un").toString(), req.get("gn").toString());
        System.out.println(" user " + user );
        // Accgroup accgroup = new Accgroup();
        try {
            if (user) {
                Accgroup accgroup = objectMapper.readValue(objectMapper.writeValueAsString(req.get("groupName")),
                        Accgroup.class);
                String userGroup = accgroup.getGroupName();
                if (userGroup != null) {
                    createAgResult = accGroupRepository.createAccgroup(accgroup);
                    System.out.println(" createAgResult " + createAgResult );
                    if (createAgResult) {
                        System.out.println(" createAgResult success " + createAgResult );
                        result.put("success", true);
                        return result;
                    }
                }
            }
        } catch (Exception error) {
            System.out.println(error);
        }
        result.put("success", false);
        return result;
    }
    
    public Map<String, Object> getAllUserGroupDTO(Map<String, Object> req) {
        List<AccgroupDTO> dtoList = new ArrayList<>();
        boolean user = checkgroup.checkgroup(req.get("un").toString(), req.get("gn").toString());
        Map<String, Object> result = new HashMap<>();
        System.out.println(" user success " + user );

        List<String> groupResult = new ArrayList<>();
        try {
            if (user) {
                List<Accgroup> accgroups = accGroupRepository.getAllAccGroup();
                groupResult = accgroups.stream().map(Accgroup::getGroupName).toList();
                result.put("success", true);

                if (accgroups != null) {
                    for (Accgroup accgroup : accgroups) {
                        dtoList.add(new AccgroupDTO(accgroup.getGroupName()));
                    }
                    result.put("groups", groupResult);
                    return result;
                }
                else{
                    result.put("groups", groupResult);
                    return result;
                }
            }
            result.put("success", false);
            result.put("groups", groupResult);
            return result;
            // else{
            //     dtoList.add(new AccgroupDTO("Permission Denied"));
            // }
        } catch (Exception e) {
            System.err.println(e);
        }
        return result;
    }

    // public List<String> getAllUserGroupDTO(Map<String, Object> req) {
    //     List<AccgroupDTO> dtoList = new ArrayList<>();
    //     boolean user = checkgroup.checkgroup(req.get("un").toString(), req.get("gn").toString());
    //     Boolean getAgResult = false;
    //     System.out.println(" user success " + user );

    //     List<String> groupResult = new ArrayList<>();
    //     try {
    //         if (user) {
    //             List<Accgroup> accgroups = accGroupRepository.getAllAccGroup();
    //             groupResult = accgroups.stream().map(Accgroup::getGroupName).toList();
    //             if (accgroups != null) {
    //                 for (Accgroup accgroup : accgroups) {
    //                     dtoList.add(new AccgroupDTO(accgroup.getGroupName()));
    //                 }

    //             }
    //             else{
    //                 dtoList.add(new AccgroupDTO("No group created"));
    //             }
    //         }
    //         else{
    //             dtoList.add(new AccgroupDTO("Permission Denied"));
    //         }
    //     } catch (Exception e) {
    //         System.err.println(e);
    //     }
    //     return groupResult;
    // }
}
