package com.tmsapp.tms.Entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "task")
public class Task {
   
    @Column
    private String taskName;
    
    @Column(columnDefinition = "TEXT")
    private String taskDescription;

    @Column(columnDefinition = "TEXT")
    private String taskNotes;

    @Id
    @Column(length=250, nullable = false)
    private String taskId;

    @Column
    private String taskState;

    @Column
    private String taskCreator;

    @Column
    private String taskOwner;

    @Column
    private Date taskCreateDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JsonIgnore
    @JoinColumn(name = "task_plan", referencedColumnName = "Plan_MVP_name")
    private Plan taskPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "Task_app_acronym", referencedColumnName = "App_Acronym")
    private Application taskAppAcronym;



    @JsonCreator
    public Task(@JsonProperty("taskName") String taskName,
                @JsonProperty("taskDescription") String taskDescription,
                @JsonProperty("taskNotes") String taskNotes,
                @JsonProperty("taskId") String taskId,
                @JsonProperty("taskPlan") Plan taskPlan,
                @JsonProperty("taskAppAcronym") Application taskAppAcronym,
                @JsonProperty("taskState") String taskState,
                @JsonProperty("taskCreator") String taskCreator,
                @JsonProperty("taskOwner") String taskOwner,
                @JsonProperty("taskCreateDate") Date taskCreateDate) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskNotes = taskNotes;
        this.taskId = taskId;
        this.taskPlan = taskPlan;
        this.taskAppAcronym = taskAppAcronym;
        this.taskState = taskState;
        this.taskCreator = taskCreator;
        this.taskOwner = taskOwner;
        this.taskCreateDate = taskCreateDate;
    }

    public Task() {
    }

    public Task(TaskDTO taskDTO, Application application, Plan plan) {
        this.taskName = taskDTO.getTaskName();
        this.taskDescription = taskDTO.getTaskDescription();
        this.taskNotes = taskDTO.getTaskNotes();
        this.taskId = taskDTO.getTaskId();
        this.taskPlan = plan;
        this.taskAppAcronym = application;
        this.taskState = taskDTO.getTaskState();
        this.taskCreator = taskDTO.getTaskCreator();
        this.taskOwner = taskDTO.getTaskOwner();
        this.taskCreateDate = taskDTO.getTaskCreateDate();
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

    public Plan getTaskPlan() {
        return taskPlan;
    }

    public void setTaskPlan(Plan taskPlan) {
        this.taskPlan = taskPlan;
    }

    public Application getTaskAppAcronym() {
        return taskAppAcronym;
    }

    public void setTaskAppAcronym(Application taskAppAcronym) {
        this.taskAppAcronym = taskAppAcronym;
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

    public void setTaskCreateDate(Date taskCreateDate) {
        this.taskCreateDate = taskCreateDate;
    }
    
}
