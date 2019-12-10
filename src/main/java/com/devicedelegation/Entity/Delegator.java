package com.devicedelegation.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashMap;

@Entity // This entity represents a delegator that acts like a virtual device.
public class Delegator {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private String timeAdded;

    private HashMap<String, Device> devices;

    // each device, there is a list of exposed endpoints that this delegator can access to.
    private HashMap<String, HashMap<String, Endpoint>> deviceEndpoints;

    // A simulated authentication string that represents device token used for binding.
    private String authentication;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(String timeAdded) {
        this.timeAdded = timeAdded;
    }

    public String getAuthentication() {return authentication;}

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public void setDevices(HashMap<String, Device> devices) {
        this.devices = devices;
    }

    public HashMap<String, Device> getDevices() {
        return devices;
    }

    public void setDeviceEndpoints(HashMap<String, HashMap<String, Endpoint>> deviceEndpoints) {
        this.deviceEndpoints = deviceEndpoints;
    }

    public HashMap<String, HashMap<String, Endpoint>> getDeviceEndpoints() {
        return deviceEndpoints;
    }
}
