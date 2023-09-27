package com.DropOff.app.service.Authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.DropOff.app.model.Authentication.UserProfile;

import java.util.HashMap;
import java.util.Map;

public interface UserProfileService {
    public UserProfile getLoggedInUser();

    public String updateUserLocation(String latitude, String longitude);

    public Map<String,String> getUserLocation(Integer id);
}
