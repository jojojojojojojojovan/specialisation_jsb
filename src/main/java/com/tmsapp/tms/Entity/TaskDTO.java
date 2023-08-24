package com.tmsapp.tms.Entity;

import java.time.LocalDate;
import java.util.Date;

public class TaskDTO {

    private String taskName;

    private String taskDescription;
   
    private String taskNotes;
    
    private String taskId;
   
    private String taskState;
  
    private String taskCreator;

    private String taskOwner;

    private Date taskCreateDate;
   
    private String taskPlan;

    private String taskAppAcronym;

    public TaskDTO(Task task) {
        this.taskName = task.getTaskName();
        this.taskDescription = task.getTaskDescription();
        this.taskNotes = task.getTaskNotes();
        this.taskId = task.getTaskId();
        this.taskState = task.getTaskState();
        this.taskCreator = task.getTaskCreator();
        this.taskOwner = task.getTaskOwner();
        this.taskCreateDate = task.getTaskCreateDate();
        this.taskPlan = (task.getTaskPlan() != null) ? task.getTaskPlan().getPlan_MVP_name() : null;
        this.taskAppAcronym = (task.getTaskAppAcronym() != null) ? task.getTaskAppAcronym().getApp_Acronym() : null;
    }

    public TaskDTO() {

    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskNotes() {
        return taskNotes;
    }

    public void setTaskNotes(String taskNotes) {
        this.taskNotes = taskNotes;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getTaskCreator() {
        return taskCreator;
    }

    public void setTaskCreator(String taskCreator) {
        this.taskCreator = taskCreator;
    }

    public String getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(String taskOwner) {
        this.taskOwner = taskOwner;
    }

    public Date getTaskCreateDate() {
        return taskCreateDate;
    }

    public void setTaskCreateDate(Date tempNow) {
        this.taskCreateDate = tempNow;
    }

    public String getTaskPlan() {
        return taskPlan;
    }

    public void setTaskPlan(String taskPlan) {
        this.taskPlan = taskPlan;
    }

    public String getTaskAppAcronym() {
        return taskAppAcronym;
    }

    public void setTaskAppAcronym(String taskAppAcronym) {
        this.taskAppAcronym = taskAppAcronym;
    }
}
