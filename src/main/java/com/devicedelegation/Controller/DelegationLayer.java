package com.devicedelegation.Controller;

import com.devicedelegation.Authenticator.SecondaryUserAuthenticator;
import com.devicedelegation.Entity.Delegator;
import com.devicedelegation.Entity.Device;
import com.devicedelegation.Entity.Endpoint;
import com.devicedelegation.Repository.DelegatorRepository;
import com.devicedelegation.Repository.DeviceRepository;
import com.devicedelegation.Repository.EndpointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller

// Delegation layer that diverts traffic from delegator to device endpoints

// Applications use this endpoint to connect to delegators, and the delegators will make corresponding endpoint calls to physical devices (if it is exposed)
@RequestMapping(path="/delagation")
public class DelegationLayer {
    @Autowired
    private DelegatorRepository delegatorRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private SecondaryUserAuthenticator secondaryUserAuthenticator;

    Logger logger = LoggerFactory.getLogger(DelegationLayer.class);

    // This is the endpoint exposed by the delegator for applications to make action calls
    @PostMapping(path="/post")
    public @ResponseBody
    ResponseEntity addDevice (@RequestParam Integer delegatorId, @RequestParam String endpoint, @RequestParam String args) {
        try {
            Delegator n = delegatorRepository.findById(delegatorId).orElse(null);
            HashMap<String, Device> deviceHashMap = n.getDevices();
            for (Map.Entry<String, Device> entry : deviceHashMap.entrySet()) {
                Device d = entry.getValue();
                HashMap<String, HashMap<String, Endpoint>> deviceEndpointsMap = n.getDeviceEndpoints();
                HashMap<String, Endpoint> deviceEndpoint = deviceEndpointsMap.getOrDefault(d.getName(), new HashMap<>());
                if(deviceEndpoint.containsKey(endpoint)) {
                    Endpoint endpointToHandle = deviceEndpoint.get(endpoint);
                    if(endpointToHandle.getSecondaryAuthentication()) {
                        if (secondaryUserAuthenticator.isAuthenticated(delegatorId, endpoint, args)) {
                            // the physical device endpoint handles the args.
                            endpointToHandle.handle(args);
                            logger.info(String.format("Delegator %s is called at Endpoint %s", n.getName(), endpointToHandle.getId()));
                        }
                    }
                }
            }



            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public void deviceEndpointConstructor(String policy, String args) {
        // This method is used for constructing the endpoint handler that makes action calls or data communications
        // with the physical device. Here we are not really implementing it in the prototype.
    }

    public boolean criticalEndpointAuthentication(Device device, String policy, String args) {
        // This method is used for providing additional authentication or user notification functions so critical endpoints
        // are getting a second confirmation from the user
        return true;
    }
}
