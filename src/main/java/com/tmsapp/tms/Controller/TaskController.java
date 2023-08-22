package com.tmsapp.tms.Controller;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmsapp.tms.Entity.Application;
import com.tmsapp.tms.Entity.Task;
import com.tmsapp.tms.Entity.TaskDTO;
import com.tmsapp.tms.Service.ApplicationService;
import com.tmsapp.tms.Service.Checkgroup;
import com.tmsapp.tms.Service.TaskService;

@RestController
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    Checkgroup checkgroup;

    @PostMapping(path = "/createTask")
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody TaskDTO task, @CookieValue("authToken") String jwtToken) {

        Map<String, Object> response = new HashMap<>();
        taskService.createTask(task, jwtToken);
        return ResponseEntity.status(HttpStatus.OK).body(response);
      
    }

    //get task by taskid
    @PostMapping(path = "/getAllTask/taskId")
    public ResponseEntity<Map<String, Object>> getTaskById(@RequestBody Map<String, Object> taskIdObj) {
        Map<String, Object> response = new HashMap<>();
        TaskDTO task = taskService.getTaskById((String) taskIdObj.get("taskId"));
        // return ResponseEntity.ok(true);
        System.out.println("task in controller" + task);
        if(task != null) {
            response.put("success", true);
            response.put("task", task);
        }
       
        return ResponseEntity.ok(response);
    }

    //get task by plan
    @PostMapping(path = "/getAllTask/plan")
    public ResponseEntity<List<TaskDTO>> getTaskByPlan(@RequestBody Map<String, Object> req) {
        String taskPlan = (String) req.get("planName");
        List<TaskDTO> tasksDTO = taskService.getTasksByPlan(taskPlan);
        // return ResponseEntity.ok(true);
       
        return ResponseEntity.ok(tasksDTO);
    }

    //get task by application
    @PostMapping(path = "/getAllTask/app")
    public ResponseEntity<Map<String, Object>> getTaskByApplication(@RequestBody Map<String, Object> req,  @CookieValue("authToken") String jwtToken) {
        Map<String, Object> response = new HashMap<>();
        
        String appAcronym = (String) req.get("appAcronym");
        List<TaskDTO> tasksDTO = taskService.getTasksByApplication(appAcronym);
        response.put("success", true);
        response.put("tasks", tasksDTO);
       
        return ResponseEntity.ok(response);
    }
    

    @GetMapping(path = "/getAllTask")
    public ResponseEntity<List<TaskDTO>> getAllTask() {
        List<TaskDTO> task = taskService.getAllTask();
        // return ResponseEntity.ok(true);
        System.out.println("task in controller" + task);
       
        return ResponseEntity.ok(task);
    }

    //INPUT: taskId, un, gn, userNotes(OPTIONAL), taskState, acronym, taskPlan(OPTIONAL)
    @PostMapping(path = "/PMEditTask")
    public ResponseEntity<Map<String, Object>> PMEditTask(@RequestBody Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        response.putAll(taskService.PMEditTask(req));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //INPUT: taskId, un, gn, userNotes(OPTIONAL), taskState, acronym, taskPlan(OPTIONAL)
    @PostMapping(path = "/PLEditTask")
    public ResponseEntity<Map<String, Object>> PLEditTask(@RequestBody Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        response.putAll(taskService.PLEditTask(req));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/testing")
    public ResponseEntity<Map<String, Object>> testing(){
        Map<String, Object> result = new HashMap<>();
        boolean userTest= checkgroup.checkgroup("user3", "Group A");
        result.put("success", userTest);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //INPUT: taskId, un, gn, userNotes(OPTIONAL), taskState, acronym
    @PostMapping(path = "/tm-update/taskToDoToDoing")
    public ResponseEntity<Map<String, Object>> TMEditTaskToDoToDoing(@RequestBody Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        response.putAll(taskService.TMEditTaskToDoToDoing(req));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PostMapping(path = "/tm-update/taskDoingToDone")
    public ResponseEntity<Map<String, Object>> TMEditTaskDoingToDone(@RequestBody Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        response.putAll(taskService.TMEditTaskDoingToDone(req));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
}
