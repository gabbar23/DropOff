package com.DropOff.app.controller.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.DropOff.app.service.Authentication.UserProfileService;

import java.util.Map;

@RestController
public class UserProfileController {
    @Autowired
    UserProfileService userService;

    /**
     * Update user location

     */
    @PutMapping("user/location/put")
    public String updateUserLocation(@RequestBody Map<String,String> req){
        return userService.updateUserLocation(req.get("latitude"),req.get("longitude"));
    }

    /**
     * Get user location
     *

     */
    @GetMapping("user/location/get")
    public Map<String,String> getUserLocation(@RequestBody Map<String,String> req){
        return userService.getUserLocation(Integer.valueOf(req.get("user_id")));
    }
}
