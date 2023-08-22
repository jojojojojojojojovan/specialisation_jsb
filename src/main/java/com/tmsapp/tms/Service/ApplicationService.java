package com.tmsapp.tms.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmsapp.tms.Entity.Accgroup;
import com.tmsapp.tms.Entity.Application;
import com.tmsapp.tms.Repository.AccgroupRepository;
import com.tmsapp.tms.Repository.ApplicationRepository;

@Service
public class ApplicationService {
    
    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    AccgroupRepository accgroupRepository;

    @Autowired
    Checkgroup checkgroup;

    public Map<String, Object> createApplication(Map<String, Object> req){
        Map<String, Object> result = new HashMap<>(); 
        //Check if user is in Project leader group 
        if(req.get("un") == null || req.get("gn") == null){
            result.put("success", false);
            result.put("message", "no un gn");
            return result;
        }
        boolean isPL =  checkgroup.checkgroup(req.get("un").toString(), req.get("gn").toString());
        if(!isPL){
            result.put("success", false);
            result.put("message", "not pl");
            return result; 
        }

        //Check for mandatory fields
        if(req.get("acronym") == null || req.get("rnumber") == null || req.get("startDate") == null || req.get("endDate") == null){
            result.put("success", false);
            result.put("message", "no appacro/rnum/star/enddatee");
            return result;
        }

        //Validate permit groups
        String open = null;
        String toDo = null;
        String doing = null;
        String done = null;
        String create = null;
        if(req.get("open") != null){
            Accgroup tempgroup = accgroupRepository.getGroupByGroupName(req.get("open").toString());
            if(tempgroup != null){
                open = tempgroup.getGroupName();
            }
        }
        if(req.get("toDo") != null){
            Accgroup tempgroup = accgroupRepository.getGroupByGroupName(req.get("toDo").toString());
            if(tempgroup != null){
                toDo = tempgroup.getGroupName();
            }
        }
        if(req.get("doing") != null){
            Accgroup tempgroup = accgroupRepository.getGroupByGroupName(req.get("doing").toString());
            if(tempgroup != null){
                doing = tempgroup.getGroupName();
            }
        }
        if(req.get("done") != null){
            Accgroup tempgroup = accgroupRepository.getGroupByGroupName(req.get("done").toString());
            if(tempgroup != null){
                done = tempgroup.getGroupName();
            }
        }
        if(req.get("create") != null){
            Accgroup tempgroup = accgroupRepository.getGroupByGroupName(req.get("create").toString());
            if(tempgroup != null){
                create = tempgroup.getGroupName();
            }
        }

        //Construct application
        Application application = new Application(req.get("acronym").toString(), req.get("description").toString(), (int) req.get("rnumber"), (Date) req.get("startDate"), (Date) req.get("endDate"), create, open, toDo, doing, done);
        Boolean isCreated = applicationRepository.createApplication(application) || false;
        if(isCreated){
            result.put("success", true);
        }
        else{
            result.put("message", "not created for some reason");
            result.put("success", false);
        }

        return result;
    }

    public Map<String, Object> getApplication(Map<String, Object> req){
        Map<String, Object> result = new HashMap<>(); 
        if(req.get("appAcronym") == null){
            result.put("success", false);
            return result;
        }
        try {
            Application application = applicationRepository.getApplication(req.get("appAcronym").toString());

            if(application == null) {
                result.put("success", false);
            } else {
                result.put("success", true);
                result.put("application", application);
            }
            return result;
        }
        catch(Exception e) {
            result.put("success", false);
            result.put("error", e.getStackTrace());
        }
        

        return result;
    }

    public Map<String, Object> updateApplication(Map<String, Object> req) throws ParseException{
        Map<String, Object> result = new HashMap<>(); 
        //Check if user is in Project leader group 
        
        if(req.get("un") == null || req.get("gn") == null){
            result.put("success", false);
            result.put("message", "no un gn");
            return result;
        }
        boolean isPL =  checkgroup.checkgroup(req.get("un").toString(), req.get("gn").toString());
        System.out.println(" pl " + isPL);
        if(!isPL){
            
            result.put("message", "not pl");
            result.put("success", false);
            return result; 
        }
        System.out.println(" application after pl  " + req.get("acronym").toString());
        //Retrieve application 
        Application application = applicationRepository.getApplication(req.get("acronym").toString());
        System.out.println(" application after get application  " + application);
        if(application == null){
            result.put("success", false);
            return result;
        }
        //update application changes
        if(req.get("endDate") != null){
            System.out.println("end date " + req.get("endDate"));
            System.out.println("end date string" + req.get("endDate").toString());
                String dateString = req.get("endDate").toString();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date temp = dateFormat.parse(dateString);

            // Date temp = (Date) req.get("endDate");
            System.out.println("temp " + temp);
            Date tempStartDate = application.getApp_startDate();
            int dateCompare = tempStartDate.compareTo(temp);
            //Start date is after end date
            if(dateCompare > 0 ){
                result.put("success", false);
                return result;

            }
            if(temp != null){
                System.out.println("temp " + temp);
                application.setApp_endDate(temp);
            }
        }
        if(req.get("create") != null){
            System.out.println("create " + req.get("create"));
            String temp = req.get("create").toString();
            if(temp != null){
                System.out.println(" temp " + temp);
                application.setApp_permit_Create(temp);
            }
        }
        if(req.get("open") != null){
            System.out.println("open " + req.get("open"));
            String temp = req.get("open").toString();
            if(temp != null){
                System.out.println(" temp " + temp);
                // application.setApp_permit_Create(temp);
                application.setApp_permit_Open(temp);
            }
        }
        if(req.get("toDo") != null){
            System.out.println("toDo " + req.get("toDo"));
            String temp = req.get("toDo").toString();
            if(temp != null){
                System.out.println(" temp " + temp);
                // application.setApp_permit_Create(temp);
                application.setApp_permit_toDoList(temp);
            }
        }
        if(req.get("doing") != null){
            System.out.println("doing " + req.get("doing"));
            String temp = req.get("doing").toString();
            if(temp != null){
                System.out.println(" temp " + temp);
                // application.setApp_permit_Create(temp);
                application.setApp_permit_Doing(temp);
            }
        }
        if(req.get("done") != null){
            System.out.println("done " + req.get("done"));
            String temp = req.get("done").toString();
            if(temp != null){
                System.out.println(" temp " + temp);
                // application.setApp_permit_Create(temp);
                application.setApp_permit_Done(temp);
            }
        }
        // if(req.get("rnumber") != null){
        //     int temp = (int) req.get("rnumber");
        //     application.setApp_Rnumber(temp);
        // }

        //Update application 
        boolean isUpdated = applicationRepository.updateApplication(application);
        if(isUpdated) result.put("success", true);
        else result.put("success", false);

        return result;
    }

    public Map<String, Object> getAllApplication(){
        Map<String, Object> result = new HashMap<>();
        List<Application> applications = applicationRepository.getAllApplication();
        result.put("applications", applications);
        result.put("success",true);

        return result;
    } 
}
