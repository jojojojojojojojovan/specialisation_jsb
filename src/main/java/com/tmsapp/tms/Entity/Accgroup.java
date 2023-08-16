package com.tmsapp.tms.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "accgroup")
public class Accgroup {
    @Id
    @Column(name = "groupName", nullable = false, length = 199)
    private String groupName;

    @JsonIgnore
    @ManyToMany(mappedBy = "accgroups", cascade = {CascadeType.ALL})
    private List<Account> accounts = new ArrayList<Account>();

    @JsonCreator
    public Accgroup(@JsonProperty("groupName") String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @JsonCreator
    public Accgroup() {

    }

    
}
