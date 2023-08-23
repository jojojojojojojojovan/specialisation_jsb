package com.tmsapp.tms.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "application")
public class Application {

    @Id
    @Column(nullable = false, length = 199)
    private String App_Acronym;

    @Column(nullable = true, columnDefinition = "LONGTEXT")
    private String App_Description;

    @Column(nullable = true)
    private Integer App_Rnumber;
    
    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    private LocalDate App_startDate;

    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    private LocalDate App_endDate;

    @Column(nullable = true)
    private String App_permit_Create;

    @Column(nullable = true)
    private String App_permit_Open;

    @Column(nullable = true)
    private String App_permit_toDoList;

    @Column(nullable = true)
    private String App_permit_Doing;

    @Column(nullable = true)
    private String App_permit_Done;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "application", fetch = FetchType.LAZY)
    @JsonIgnore
    private List <Plan> plans;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskAppAcronym", fetch = FetchType.LAZY)
    private List <Task> tasks;

    @JsonCreator
    public Application(@JsonProperty("app_Acronym") String app_Acronym, 
                    @JsonProperty("app_Description")String app_Description, 
                    @JsonProperty("app_Rnumber") int app_Rnumber, 
                    @JsonProperty("app_startDate") LocalDate app_startDate,
                    @JsonProperty("app_endDate") LocalDate app_endDate, 
                    @JsonProperty("app_permit_Create") String app_permit_Create, 
                    @JsonProperty("app_permit_Open") String app_permit_Open, 
                    @JsonProperty("app_permit_toDoList") String app_permit_toDoList,
                    @JsonProperty("app_permit_Doing") String app_permit_Doing, 
                    @JsonProperty("app_permit_Done") String app_permit_Done) {
        App_Acronym = app_Acronym;
        App_Description = app_Description;
        App_Rnumber = app_Rnumber;
        App_startDate = app_startDate;
        App_endDate = app_endDate;
        App_permit_Create = app_permit_Create;
        App_permit_Open = app_permit_Open;
        App_permit_toDoList = app_permit_toDoList;
        App_permit_Doing = app_permit_Doing;
        App_permit_Done = app_permit_Done;
    }

    @JsonCreator
    public Application() {};

    

    @Override
    public String toString() {
        return "Application [App_Acronym=" + App_Acronym + ", App_Description=" + App_Description + ", App_Rnumber="
                + App_Rnumber + ", App_startDate=" + App_startDate + ", App_endDate=" + App_endDate
                + ", App_permit_Create=" + App_permit_Create + ", App_permit_Open=" + App_permit_Open
                + ", App_permit_toDo=" + App_permit_toDoList + ", App_permit_Doing=" + App_permit_Doing
                + ", App_permit_Done=" + App_permit_Done + ", plans=" + plans + ", tasks=" + tasks + "]";
    }

    public String getApp_Acronym() {
        return App_Acronym;
    }

    public void setApp_Acronym(String app_Acronym) {
        App_Acronym = app_Acronym;
    }

    public String getApp_Description() {
        return App_Description;
    }

    public void setApp_Description(String app_Description) {
        App_Description = app_Description;
    }

    public int getApp_Rnumber() {
        return App_Rnumber;
    }

    public void setApp_Rnumber(int app_Rnumber) {
        App_Rnumber = app_Rnumber;
    }

    public LocalDate getApp_startDate() {
        return App_startDate;
    }

    public void setApp_startDate(LocalDate app_startDate) {
        App_startDate = app_startDate;
    }

    public LocalDate getApp_endDate() {
        return App_endDate;
    }

    public void setApp_endDate(LocalDate app_endDate) {
        App_endDate = app_endDate;
    }

    public String getApp_permit_Create() {
        return App_permit_Create;
    }

    public void setApp_permit_Create(String app_permit_Create) {
        App_permit_Create = app_permit_Create;
    }

    public String getApp_permit_Open() {
        return App_permit_Open;
    }

    public void setApp_permit_Open(String app_permit_Open) {
        App_permit_Open = app_permit_Open;
    }

    public String getApp_permit_toDoList() {
        return App_permit_toDoList;
    }

    public void setApp_permit_toDoList(String app_permit_toDo) {
        App_permit_toDoList = app_permit_toDo;
    }

    public String getApp_permit_Doing() {
        return App_permit_Doing;
    }

    public void setApp_permit_Doing(String app_permit_Doing) {
        App_permit_Doing = app_permit_Doing;
    }

    public String getApp_permit_Done() {
        return App_permit_Done;
    }

    public void setApp_permit_Done(String app_permit_Done) {
        App_permit_Done = app_permit_Done;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
}
