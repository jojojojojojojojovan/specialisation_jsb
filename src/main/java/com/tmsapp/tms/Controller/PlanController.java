package com.tmsapp.tms.Controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.tmsapp.tms.Entity.TaskDTO;
import com.tmsapp.tms.Service.PlanService;

@RestController
@CrossOrigin(origins = "*")
public class PlanController {

    @Autowired
    PlanService planService;

    //INPUT: un, appAcronym, plan name, start date, end date, colour
    @PostMapping(path = "/createPlan")
    public ResponseEntity<Map<String, Object>> createPlan(@RequestBody Map<String, Object> req) {
        Map<String, Object> result = new HashMap<>();

        //Call account service method
        result.putAll(planService.createPlan(req));
            
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //INPUT:
    @GetMapping(path = "/getAllPlans")
    public ResponseEntity<Map<String, Object>> getAllPlans() {
        Map<String, Object> result = new HashMap<>();

        //Call account service method
        result.putAll(planService.getAllPlan());
            
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //INPUT: appAcronym
    @PostMapping(path = "/all-plan/app")
    public ResponseEntity<Map<String, Object>> getAllPlansByApp(@RequestBody Map<String, Object> req) {
        Map<String, Object> result = new HashMap<>();

        //Call account service method
        result = (planService.getPlansByApp(req));
        System.out.println(req);
            
        return ResponseEntity.ok(result);
    }

    //INPUT: planName
    @PostMapping(path = "/get-plan/planname")
    public ResponseEntity<Map<String, Object>> getAllPlanByPlanName(@RequestBody Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        response.putAll(planService.getPlanByPlanName(req));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
