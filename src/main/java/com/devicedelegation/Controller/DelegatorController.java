package com.devicedelegation.Controller;

import com.devicedelegation.Entity.Delegator;
import com.devicedelegation.Entity.Device;
import com.devicedelegation.Entity.Endpoint;
import com.devicedelegation.Repository.DelegatorRepository;
import com.devicedelegation.Repository.DeviceRepository;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller    // Controller for delegator
@RequestMapping(path="/delegator")
public class DelegatorController {
    @Autowired
    private DelegatorRepository delegatorRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    // action to create a delegator
    @PostMapping(path="/add")
    public @ResponseBody ResponseEntity addNewDelegator (@RequestParam String name, @RequestParam String authentication) {
        try {
            Delegator n = new Delegator();
            n.setName(name);

            Date date = new Date();
            n.setTimeAdded(dateFormat.format(date));
            n.setAuthentication(authentication);
            delegatorRepository.save(n);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // action to update a device to this delegator
    @PostMapping(path="/adddevice")
    public @ResponseBody ResponseEntity addDevice (@RequestParam Integer delegatorId, @RequestParam Integer deviceId) {
        try {
            Delegator n = delegatorRepository.findById(delegatorId).orElse(null);
            HashMap<String, Device> deviceHashMap = n.getDevices();
            Device d = deviceRepository.findById(deviceId).orElse(null);
            deviceHashMap.put(d.getName(), d);

            HashMap<String, HashMap<String, Endpoint>> deviceEndpoints = n.getDeviceEndpoints();
            HashMap<String, Endpoint> endpoints = deviceEndpoints.getOrDefault(d.getName(), new HashMap<>());

            n.setDeviceEndpoints(deviceEndpoints);

            delegatorRepository.save(n);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // action to update a device to this delegator
    @PostMapping(path="/adddeviceendpoint")
    public @ResponseBody ResponseEntity addDeviceEndpoint (@RequestParam Integer delegatorId, @RequestParam String device, @RequestParam String endpointName, @RequestParam String endpointPolicy) {
        try {
            Endpoint endpoint = buildEndpoint(endpointPolicy);
            Delegator n = delegatorRepository.findById(delegatorId).orElse(null);
            HashMap<String, HashMap<String, Endpoint>> deviceEndpoints = n.getDeviceEndpoints();
            HashMap<String, Endpoint> endpoints = deviceEndpoints.getOrDefault(device, new HashMap<>());
            endpoints.put(endpointName, endpoint);

            delegatorRepository.save(n);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // action to list all devices under a delegator
    @GetMapping(path="/listdevices")
    public @ResponseBody Iterable<Device> getAllDevices(@RequestParam Integer delegatorId) {
        HashMap<String, Device> devices =  delegatorRepository.findById(delegatorId).get().getDevices();
        Iterable<Device> deviceIterable = new ArrayList<>();
        for (Map.Entry<String, Device> device : devices.entrySet()) {
            ((ArrayList<Device>) deviceIterable).add(device.getValue());
        }
        return deviceIterable;
    }

    // action to list all delegators
    @GetMapping(path="/list")
    public @ResponseBody Iterable<Delegator> getAllDelegators() {
        return delegatorRepository.findAll();
    }

    // a helper function that builds the endpoint from the policy provided
    public static Endpoint buildEndpoint(String policy) {
        // This is a simulated function, the actual building function may vary depending on the types of handlers or
        // frameworks being used.

        Endpoint endpoint = new Endpoint();
        endpoint.setPolicy(policy);
        return endpoint;
    }
}
