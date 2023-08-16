package com.tmsapp.tms.Entity;

import java.util.List;

public class AccountDTO {
    private String username;
    private String password;
    private String email;
    private int status;
    private List<Accgroup> accgroupNames; // Store group names instead of the full Accgroup objects

    public AccountDTO(String username, String password, String email, int status, List<Accgroup> accgroupNames) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
        this.accgroupNames = accgroupNames;
    }

    // Getters and setters
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

    public List<Accgroup> getAccgroupNames() {
        return accgroupNames;
    }

    public void setAccgroupNames(List<Accgroup> accgroupNames) {
        this.accgroupNames = accgroupNames;
    }
}
