package com.tmsapp.tms.Entity;

import java.time.LocalDate;
import java.util.Date;

public class PlanDTO {
    private String plan_MVP_name;

    private LocalDate plan_startLocalDateTime;

    private LocalDate plan_endLocalDateTime;

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

    public LocalDate getPlan_startLocalDateTime() {
        return plan_startLocalDateTime;
    }

    public void setPlan_startLocalDateTime(LocalDate plan_startLocalDateTime) {
        this.plan_startLocalDateTime = plan_startLocalDateTime;
    }

    public LocalDate getPlan_endLocalDateTime() {
        return plan_endLocalDateTime;
    }

    public void setPlan_endLocalDateTime(LocalDate plan_endLocalDateTime) {
        this.plan_endLocalDateTime = plan_endLocalDateTime;
    }


    public String getAppAcronym() {
        return appAcronym;
    }

    public void setAppAcronym(String appAcronym) {
        this.appAcronym = appAcronym;
    }

    public PlanDTO( String plan_MVP_name, 
                 LocalDate plan_startLocalDateTime, 
                 LocalDate plan_endLocalDateTime,
                 String colour) {
        this.plan_MVP_name = plan_MVP_name;
        this.plan_startLocalDateTime = plan_startLocalDateTime;
        this.plan_endLocalDateTime = plan_endLocalDateTime;
        this.colour = colour;
    }

    public PlanDTO() {};

    


}
