package com.tmsapp.tms.Service;


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
        if(req.get("startDate") == null || req.get("endDate") == null || req.get("planName") == null || req.get("colour") == null || req.get("appAcronym") == null){
            result.put("success", false);
            return result;
        }

        //Get application
        Application application = applicationRepository.getApplication(req.get("appAcronym").toString());
        if(application == null){
            result.put("success", false);
            return result;
        }
        //Check if user has permit open 
        if(req.get("un") == null){
            result.put("success", false);
            return result;
        }
        boolean isPM =  checkgroup.checkgroup(req.get("un").toString(), application.getApp_permit_Open());
        if(!isPM){
            result.put("success", false);
            return result; 
        }

        //Create plan 
        Plan plan = new Plan(req.get("planName").toString(), (Date) req.get("startDate"), (Date) req.get("endDate"), req.get("colour").toString());

        //Validate: plan start date and end date must be between application start date and end date
        
        //Date check 1
        int planStartDateCompareEndDate = plan.getPlan_startDate().compareTo(plan.getPlan_endDate());
        if(planStartDateCompareEndDate > 0){
            result.put("success", false);
            result.put("message", "Plan Start date is after plan end date");
            return result;
        }

        //Date check 2
        int appStartDateComparePlanStartDate = application.getApp_startDate().compareTo(plan.getPlan_startDate());
        if(appStartDateComparePlanStartDate < 0){
            result.put("success", false);
            result.put("message", "Application start date must be before plan start date");
            return result;
        }

        //Date check 3
        int appEndDateComparePlanStartDate = application.getApp_endDate().compareTo(plan.getPlan_startDate());
        if(appEndDateComparePlanStartDate < 0 ){
            result.put("success", false);
            result.put("message", "Application end date must be after plan start date");
            return result;
        }

        //Date check 4
        int appEndDateComparePlanEndDate = application.getApp_endDate().compareTo(plan.getPlan_endDate());
        if(appEndDateComparePlanEndDate < 0 ){
            result.put("success", false);
            result.put("message", "Application end date must be after plan end date");
            return result;
        }

        boolean isCreated = planRepository.createPlan(plan);
        if(isCreated)result.put("success", true);
        else result.put("success", false);

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
