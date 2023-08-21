package com.tmsapp.tms.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmsapp.tms.Entity.Application;
import com.tmsapp.tms.Entity.Plan;
import com.tmsapp.tms.Entity.Task;
import com.tmsapp.tms.Entity.TaskDTO;
import com.tmsapp.tms.Repository.ApplicationRepository;
import com.tmsapp.tms.Repository.PlanRepository;
import com.tmsapp.tms.Repository.TaskRepository;
import com.tmsapp.tms.Util.ApplicationConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private Checkgroup checkGroup;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, Object> createTask(TaskDTO task, String jwtToken) {

        // TaskDTO task = objectMapper.readValue(objectMapper.writeValueAsString(req.get("task")), TaskDTO.class);

        // String jwtToken = req.get("jwtToken");

        Map<String, Object> response = new HashMap<>();

        Map<String, Object> appAcronymObj = new HashMap<>();
        appAcronymObj.put("appAcronym", task.getTaskAppAcronym());

        Map<String, Object> applicationObj = applicationService.getApplication(appAcronymObj);

        Application application = new Application();

        if((boolean) applicationObj.get("success") == false) {
            response.put("success", false);
            response.put("message", "Failed to get application");
            return response;
        }
        try {
            application = objectMapper.readValue(objectMapper.writeValueAsString(applicationObj.get("application")), Application.class);
        }
        catch(Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return response;
        }
        

        try {
            Jws<Claims> jwtContents = Jwts.parserBuilder()
                .setSigningKey(ApplicationConstant.SECURITY_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(jwtToken);

                System.out.println(jwtContents);
                String username = jwtContents.getBody().getSubject();
                System.out.println("username: " + username);
                System.out.println("username: " + application.getApp_permit_Create());

                boolean authorized = checkGroup.checkgroup(username, application.getApp_permit_Create());
                System.out.println(authorized);
                if(!authorized) {
                    response.put("success", false);
                    response.put("message", "User does not have permission");
                    return response;
                }

            // Account user =  accountRepository.getAccountByUsername(username);
        }
        catch(Exception e) {
            response.put("success", false);
            response.put("message", "User does not have permission");
            return response;
        }

        //Get & check fields 
        String taskName, taskDescription, taskNotes, taskId, taskPlan, taskApp, taskState, taskCreator, taskOwner, createDate;
        
        // Application application = applicationRepository.getApplication(task.getTaskAppAcronym());
        String appAcronym =application.getApp_Acronym();

        Plan plan = planRepository.getPlansByPlanName(task.getTaskPlan());

        //Input validation
        if(task.getTaskName() == null || task.getTaskAppAcronym() == null || task.getTaskCreator() == null || task.getTaskOwner() == null){
            throw new Error("Missing mandatory field");
        }

        // System generate task note
        Date tempNow = new Date();
        String systemNotes = "system|open|" + tempNow.toInstant().toString() + "|Task created";
        taskNotes = systemNotes;
        System.out.println(task.getTaskNotes());
        if (task.getTaskNotes() != null) {
            String notesRegex = "\\|";
            if (!task.getTaskNotes().matches(notesRegex)) {
                System.out.println("user notes present");
                String userNotes = task.getTaskCreator() + "|open|" + tempNow.toInstant().toString() + "|" + task.getTaskNotes();
                // System.out.println(userNotes);
                taskNotes += "||" + userNotes;
                // System.out.println(taskNotes);
            }
        }

        task.setTaskNotes(taskNotes);

        //system generate taskId from application Rnumber
        Integer appRNumber = applicationRepository.getApplicationRNumber(appAcronym);
        taskId = appAcronym+"_"+ String.valueOf(appRNumber);
        task.setTaskId(taskId);
        //update appRNumber;
        application.setApp_Rnumber(appRNumber + 1);
        applicationRepository.updateApplication(application);

        try {
            System.out.println("applicsation in service: " + application);
            System.out.println("plan in service: " + plan);
            taskRepository.createTask(new Task(task, application, plan));
            response.put("success", true);
            return response;
        }
        catch(Error err) {
            System.err.println(err);
            response.put("success", false);
            response.put("message", "Failed to create Task");
            return response;
        }
    }

    
    public TaskDTO getTaskById(String taskId) {
        TaskDTO task = new TaskDTO();
        try {
            task = taskRepository.getTaskById(taskId);
        }
        catch (Error err) {
            System.err.println(err);
        }

        return task;
    }

    public List<TaskDTO> getTasksByPlan(String taskPlan) {
        List<TaskDTO> tasksDTO = new ArrayList<>();
        try {
            tasksDTO = taskRepository.getTasksByPlan(taskPlan);
        }
        catch (Error err) {
            System.err.println(err);
        }

        return tasksDTO;
    }

    public List<TaskDTO> getTasksByApplication(String appAcronym) {
        List<TaskDTO> tasksDTO = new ArrayList<>();
        try {
            tasksDTO = taskRepository.getTasksByApplication(appAcronym);
        }
        catch (Error err) {
            System.err.println(err);
        }

        return tasksDTO;
    }

    @Transactional
    public List<TaskDTO> getAllTask() {
        List<TaskDTO> taskList = new ArrayList<TaskDTO>();

        try {
            taskList = taskRepository.getAllTask();
        }
        catch (Error err) {
            System.err.println(err);
        }

        return taskList;
    }

    public Map<String, Object> PMEditTask (Map<String, Object> req){
        Map<String, Object> response = new HashMap<>();
        //Check for required fields 
        if(req.get("taskId") == null || req.get("un") == null || req.get("gn") == null || req.get("taskState") == null || req.get("acronym") == null){
            response.put("success", false);
            response.put("message", "mandatory fields missing");
            return response;
        }

        //check if user is pm 
        boolean isPM = checkGroup.checkgroup(req.get("un").toString(), req.get("gn").toString());
        if(!isPM){
            response.put("success", false);
            response.put("message", "unauthorized (NOT PM)");
            return response;  
        }

        //Get application and Task
        Application application = applicationRepository.getApplication(req.get("acronym").toString());
        TaskDTO task = taskRepository.getTaskById(req.get("taskId").toString());
        Plan newPlan = null;
        if(application == null || task == null){
            response.put("success", false);
            response.put("message", "No available task/application");
            return response;
        }

        //Check if plan exisit if req.get("taskPlan") != null
        if(req.get("taskPlan") !=null){
            newPlan = planRepository.getPlansByPlanName(req.get("taskPlan").toString());
            if(newPlan == null){
                response.put("success", false);
                response.put("message", "No exisiting plan");
                return response;
            }
            task.setTaskPlan(newPlan.getPlan_MVP_name());
        }else{
            newPlan = planRepository.getPlansByPlanName(task.getTaskPlan());
        }

        //Get current date
        Date tempDateNow = new Date();

        //System/User's notes
        String systemNotes = null;
        String userNotes = null;
        //task state 
        //Check current task state as open
        System.out.println(task.getTaskState());
        if(!task.getTaskState().toLowerCase().equals("open")){
            response.put("success", false);
            response.put("message", "Current task is not in open state");
            return response;
        }
        if(task.getTaskState().toLowerCase() != req.get("taskState").toString().toLowerCase()){
            systemNotes = "system|" + req.get("un").toString().toLowerCase() + "|" + tempDateNow.toInstant().toString() + "| Updated task state||";
            task.setTaskState(req.get("taskState").toString());
        }
        if(req.get("userNotes") != null){
            systemNotes = "system|" + req.get("un").toString().toLowerCase() + "|" + tempDateNow.toInstant().toString() + "| Updated task user notes||";
            userNotes = req.get("un").toString() + "|" + task.getTaskState() + "|" + tempDateNow.toInstant().toString() + req.get("userNotes") + "||";
        }
        if(systemNotes != null){
            task.setTaskNotes(task.getTaskNotes().concat(systemNotes));
        }
        if(userNotes != null){
            task.setTaskNotes(task.getTaskNotes().concat(userNotes));
        }

        //Update task creator
        task.setTaskOwner(req.get("un").toString());

        //update task
        Task updateTask = new Task(task, application, newPlan);
        boolean isUpdated = taskRepository.updateTask(updateTask);
        
        //Return
        if(isUpdated){
            response.put("success", true);
            return response;
        }

        response.put("success", false);
        return response;
    }


}
