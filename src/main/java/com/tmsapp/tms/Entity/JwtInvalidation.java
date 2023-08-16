package com.tmsapp.tms.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "jwt_invalidation")
public class JwtInvalidation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(columnDefinition = "TEXT", nullable = false)
    private String jwtToken;

    public JwtInvalidation(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public JwtInvalidation(){
        
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
