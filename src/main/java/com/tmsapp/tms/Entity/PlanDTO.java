package com.tmsapp.tms.Entity;

import java.util.Date;

public class PlanDTO {
    private String plan_MVP_name;

    private Date plan_startDate;

    private Date plan_endDate;

    private String colour;

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    private String appAcronym;

    public String getPlan_MVP_name() {
        return plan_MVP_name;
    }

    public void setPlan_MVP_name(String plan_MVP_name) {
        this.plan_MVP_name = plan_MVP_name;
    }

    public Date getPlan_startDate() {
        return plan_startDate;
    }

    public void setPlan_startDate(Date plan_startDate) {
        this.plan_startDate = plan_startDate;
    }

    public Date getPlan_endDate() {
        return plan_endDate;
    }

    public void setPlan_endDate(Date plan_endDate) {
        this.plan_endDate = plan_endDate;
    }


    public String getAppAcronym() {
        return appAcronym;
    }

    public void setAppAcronym(String appAcronym) {
        this.appAcronym = appAcronym;
    }

    public PlanDTO( String plan_MVP_name, 
                 Date plan_startDate, 
                 Date plan_endDate,
                 String colour) {
        this.plan_MVP_name = plan_MVP_name;
        this.plan_startDate = plan_startDate;
        this.plan_endDate = plan_endDate;
        this.colour = colour;
    }

    public PlanDTO() {};

    


}
