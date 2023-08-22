package com.tmsapp.tms.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

import com.tmsapp.tms.Service.ApplicationService;

@RestController
@CrossOrigin(origins = "*")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    //INPUT: appAcronym
    @PostMapping("/getApplication")
    public ResponseEntity<Map<String, Object>> getApplication(@RequestBody Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        response.putAll(applicationService.getApplication(req));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //INPUT: un, gn, appAcronym, start date, end date, rnumber, description(OPTIONAL), create(OPTIONAL), open(OPTIONAL), todo(OPTIONAL), doing(OPTIONAL), done(OPTIONAL)
    @PostMapping("/createApplication")
    public ResponseEntity<Map<String, Object>> createApplication(@RequestBody Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        response.putAll(applicationService.createApplication(req));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //INPUT: un, gn, acronym, end date, create(OPTIONAL), open(OPTIONAL), todo(OPTIONAL), doing(OPTIONAL), done(OPTIONAL)
    @PostMapping("/updateApplication")
    public ResponseEntity<Map<String, Object>> updateApplication(@RequestBody Map<String, Object> req) throws ParseException {
        Map<String, Object> response = new HashMap<>();
        response.putAll(applicationService.updateApplication(req));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //INPUT: 
    @PostMapping("/getAllApplication")
    public ResponseEntity<Map<String, Object>> getAllApplication() {
        Map<String, Object> response = new HashMap<>();
        response.putAll(applicationService.getAllApplication());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
