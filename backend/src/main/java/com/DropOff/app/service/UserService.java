package com.DropOff.app.service;

import com.DropOff.app.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

public interface UserService {
    public User getLoggedInUser();

    public String updateUserLocation(String latitude, String longitude);

    public Map<String,String> getUserLocation(Integer id);
}
