package com.tmsapp.tms.Entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ApplicationDTO {
    private String App_Acronym;
    private String App_Description;
    private Integer App_Rnumber;
    private LocalDate App_startDate;
    private LocalDate App_endDate;
    private String App_permit_Create;
    private String App_permit_Open;
    private String App_permit_toDoList;
    private String App_permit_Doing;
    private String App_permit_Done;

    public ApplicationDTO (String app_Acronym, 
                             String app_Description, 
                             int app_Rnumber, 
                             LocalDate app_startDate,
                             LocalDate app_endDate, 
                             String app_permit_Create, 
                             String app_permit_Open, 
                             String app_permit_toDoList,
                             String app_permit_Doing, 
                             String app_permit_Done) {
        this.App_Acronym = app_Acronym;
        this.App_Description = app_Description;
        this.App_Rnumber = app_Rnumber;
        this.App_startDate = app_startDate;
        this.App_endDate = app_endDate;
        this.App_permit_Create = app_permit_Create;
        this.App_permit_Open = app_permit_Open;
        this.App_permit_toDoList = app_permit_toDoList;
        this.App_permit_Doing = app_permit_Doing;
        this.App_permit_Done = app_permit_Done;
    }

    // Add getters and setters for all fields
    public ApplicationDTO() {};

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

    public void setApp_permit_toDoList(String app_permit_toDoList) {
        App_permit_toDoList = app_permit_toDoList;
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
}



