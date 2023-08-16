package com.tmsapp.tms.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(nullable = false, length = 199)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;

    @Column(nullable = false)
    private int status;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "account_group",
        joinColumns = {
            @JoinColumn(name="username")
        },
        inverseJoinColumns = {
            @JoinColumn(name="groupName")
        }
    )
    List<Accgroup> accgroups = new ArrayList<Accgroup>();

    @JsonCreator
    public Account(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("status") int status,@JsonProperty("email")  String email, @JsonProperty("groups")List<Accgroup> accgroups) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.accgroups = accgroups;
        this.email = email;
    }

    @JsonCreator
    public Account(){
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Accgroup> getAccgroups() {
        return accgroups;
    }

    public void setAccgroups(List<Accgroup> accgroups) {
        this.accgroups = accgroups;
    }

}
