package com.tmsapp.tms.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.AccgroupDTO;
import com.tmsapp.tms.Service.AccgroupService;

@RestController
@CrossOrigin(origins = "*")

public class AccgroupController {

    @Autowired
    AccgroupService accGroupService;

    @PostMapping(path = "/createAccGroup")
    public ResponseEntity<Map<String, Object>> createAccGroup(@RequestBody Map<String, Object> req) {
        Map<String, Object> result = new HashMap<>();
        result = accGroupService.createAccgroup(req);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
    @PostMapping(path = "/getAllGroups")
    public ResponseEntity<Map<String,Object>> getAllGroups(@RequestBody Map<String, Object> req) {
        Map<String, Object> result = new HashMap<>();
        result.putAll(accGroupService.getAllUserGroupDTO(req));
        return ResponseEntity.status(HttpStatus.OK).body(result);
        // List<String> dtoList = accGroupService.getAllUserGroupDTO(req);
        // return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    // @PostMapping(path = "/getAllGroups")
    // public ResponseEntity<List<String>> getAllGroups(@RequestBody Map<String, Object> req) {
    //     List<String> dtoList = accGroupService.getAllUserGroupDTO(req);
    //     return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    // }

}
