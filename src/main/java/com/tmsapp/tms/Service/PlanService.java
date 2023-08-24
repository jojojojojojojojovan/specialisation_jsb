package com.tmsapp.tms.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmsapp.tms.Entity.Application;
import com.tmsapp.tms.Entity.Plan;
import com.tmsapp.tms.Repository.ApplicationRepository;
import com.tmsapp.tms.Repository.PlanRepository;

@Service
public class PlanService {
    @Autowired
    PlanRepository planRepository;
    
    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    Checkgroup checkgroup;

    public Map<String, Object> createPlan(Map<String, Object> req){
        Map<String, Object> result = new HashMap<>();
        //Check if user is in Project manager Group
        if(req.get("un") == null || req.get("gn") == null){
            result.put("success", false);
            result.put("message", "no un gn");
            return result;
        }
        boolean isPM =  checkgroup.checkgroup(req.get("un").toString(), req.get("gn").toString());
        if(!isPM){
            result.put("success", false);
            result.put("message", "not pm");
            return result; 
        }

        //Check for mandatory fields
        if(req.get("startDate") == null || req.get("endDate") == null || req.get("planName") == null || req.get("colour") == null || req.get("appAcronym") == null){
            result.put("success", false);
            result.put("message", "missing madatory fields");
            return result;
        }

        //Get application
        Application application = applicationRepository.getApplication(req.get("appAcronym").toString());
        if(application == null){
            result.put("success", false);
            result.put("message", "app not found");
            return result;
        }

        //Create plan 
        Plan plan;
        try {
            plan = new Plan(req.get("planName").toString(), LocalDate.parse(req.get("startDate").toString()), LocalDate.parse(req.get("endDate").toString()), req.get("colour").toString());
            plan.setApplication(application);
        } catch (Exception e) {    
            result.put("success", false);
            result.put("message","invalid date format");
            return result;
        }

        //Validate: plan start date and end date must be between application start date and end date
        
        //LocalDate check 1
        int planStartDateCompareEndDate = plan.getPlan_startDate().compareTo(plan.getPlan_endDate());
        if(planStartDateCompareEndDate > 0){
            result.put("success", false);
            result.put("message", "Plan Start date is before plan end date");
            return result;
        }

        //LocalDate check 2
        int appStartDateComparePlanStartDate = application.getApp_startDate().compareTo(plan.getPlan_startDate());
        if(appStartDateComparePlanStartDate > 0){
            result.put("success", false);
            result.put("message", "Application start date must be before plan start date");
            return result;
        }

        //LocalDate check 3
        int appEndDateComparePlanStartDate = application.getApp_endDate().compareTo(plan.getPlan_startDate());
        if(appEndDateComparePlanStartDate < 0 ){
            result.put("success", false);
            result.put("message", "Application end date must be after plan start date");
            return result;
        }

        //LocalDate check 4
        int appEndDateComparePlanEndDate = application.getApp_endDate().compareTo(plan.getPlan_endDate());
        if(appEndDateComparePlanEndDate < 0 ){
            result.put("success", false);
            result.put("message", "Application end date must be after plan end date");
            return result;
        }

        result = planRepository.createPlan(plan);
        return result;
    }

    public Map<String, Object> getAllPlan(){
        Map<String, Object> result = new HashMap<>();
        List<Plan> plans = planRepository.getAllPlan();
        result.put("plans", plans);
        result.put("success", true);
        return result;
    }

    public Map<String, Object> getPlansByApp(Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        String appAcronym = (String) req.get("appAcronym");

        List<Plan> plans = planRepository.getPlansByAppAcronym(appAcronym);
        response.put("plans", plans);
        response.put("success", true);
        return response;
    }

    public Map<String, Object> getPlanByPlanName(Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        if(req.get("planName") == null){
            response.put("success", false);
            return response;
        }

        Plan plans = planRepository.getPlansByPlanName(req.get("planName").toString());
        System.out.println("PLAN NAME!!!!");
        System.out.println(plans.getPlan_MVP_name());
        response.put("success", true);
        response.put("plans", plans);
        return response;
    }
}
