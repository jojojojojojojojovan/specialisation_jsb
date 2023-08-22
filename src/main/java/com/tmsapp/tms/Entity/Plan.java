package com.tmsapp.tms.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "plan")
public class Plan {
    @Id
    @Column(length = 199, nullable = false)
    private String Plan_MVP_name;

    @Column(columnDefinition = "DATE", nullable = false)
    private Date Plan_startDate;

    @Column(columnDefinition = "DATE", nullable = false)
    private Date Plan_endDate;

    @Column(nullable = false)
    private String Colour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "Plan_app_acronym")
    private Application application;

    @JsonIgnore
    @OneToMany(mappedBy = "taskPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

    @JsonCreator
    public Plan(@JsonProperty("plan_MVP_name") String plan_MVP_name, 
                @JsonProperty("plan_startDate") Date plan_startDate, 
                @JsonProperty("plan_endDate") Date plan_endDate,
                @JsonProperty("colour") String colour) {
        Plan_MVP_name = plan_MVP_name;
        Plan_startDate = plan_startDate;
        Plan_endDate = plan_endDate;
        Colour = colour;
    }

    @JsonCreator
    public Plan() {};

    public String getPlan_MVP_name() {
        return Plan_MVP_name;
    }

    public void setPlan_MVP_name(String plan_MVP_name) {
        Plan_MVP_name = plan_MVP_name;
    }

    public Date getPlan_startDate() {
        return Plan_startDate;
    }

    public void setPlan_startDate(Date plan_startDate) {
        Plan_startDate = plan_startDate;
    }

    public Date getPlan_endDate() {
        return Plan_endDate;
    }

    public void setPlan_endDate(Date plan_endDate) {
        Plan_endDate = plan_endDate;
    }

    public String getColour() {
        return Colour;
    }

    public void setColour(String colour) {
        Colour = colour;
    }

}
