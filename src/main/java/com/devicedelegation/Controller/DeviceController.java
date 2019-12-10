package com.devicedelegation.Controller;

import com.devicedelegation.Entity.Device;
import com.devicedelegation.Repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller    // Controller for device
@RequestMapping(path="/device")
public class DeviceController {
    @Autowired
    private DeviceRepository deviceRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewDevice (@RequestParam String name, @RequestParam String timeAdded, @RequestParam String authentication) {
        Device n = new Device();
        n.setName(name);
        n.setTimeAdded(timeAdded);
        n.setAuthentication(authentication);
        deviceRepository.save(n);
        return "Saved";
    }

    @PostMapping(path="/delete")
    public @ResponseBody String deleteDevice (@RequestParam Integer deviceId) {
        deviceRepository.deleteById(deviceId);
        return "Deleted";
    }

    @GetMapping(path="/list")
    public @ResponseBody Iterable<Device> getAllDevices() {
        // This returns a JSON or XML with the users
        return deviceRepository.findAll();
    }
}
