package com.devicedelegation.Entity;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This entity represents an endpoint that allows the delegator to make calls to the device handler.
public class Endpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String policy;

    private Boolean secondaryAuthentication;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getSecondaryAuthentication() {
        return secondaryAuthentication;
    }

    public void setSecondaryAuthentication(Boolean secondaryAuthentication) {
        this.secondaryAuthentication = secondaryAuthentication;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }


    public void handle(String args) {
        // in real implementation, this function handles the method calls towards the physical device
    }

}
