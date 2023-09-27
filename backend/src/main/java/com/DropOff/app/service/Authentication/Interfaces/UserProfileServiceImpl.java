package com.DropOff.app.service.Authentication;

import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.repository.Authentication.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    UserRepository userRepo;

    /**
     * Get logged in user
     *
     * 
     * @return user who is logged in
     */
    @Override
    public UserProfile getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user_email = auth.getPrincipal().toString();

        UserProfile user = userRepo.getUserByEmail(user_email);

        return user;
    }

    /**
     * Update the user coordinates in database
     *
     * 
     * @return string response
     */
    @Override
    public String updateUserLocation(String latitude, String longitude){
        UserProfile user = getLoggedInUser();

        user.setLocation(latitude,longitude);

        userRepo.save(user);

        return "Location updated";
    }

    /**
     * Get the user coordinates
     *
     * 
     * @return map coordinatess
     */
    @Override
    public Map<String,String> getUserLocation(Integer id){
        UserProfile user = userRepo.getUserById(id);
        if(user != null){
            String latitude = user.getLatitude();
            String longitude = user.getLongitude();

            Map<String,String> coordinates = new HashMap<>();
            coordinates.put("latitude",latitude);
            coordinates.put("longitude",longitude);

            return coordinates;
        }
        return null;
    }
}
