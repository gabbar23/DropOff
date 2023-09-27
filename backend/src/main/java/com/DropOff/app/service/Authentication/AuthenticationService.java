package com.DropOff.app.service.Authentication;

import com.DropOff.app.model.Authentication.UserProfile;


public interface AuthenticationService {
    public boolean isAlreadyExist(UserProfile user);

    public String resetPassword(String token, String password);

    public String forgotPassword(String email);
    public String verifyEmail(String code, int id);

    public String register(UserProfile new_user);

    public String login(String email, String password);

    public UserProfile getUser(int id);

    public UserProfile getUserInfo(String token);
    public String updateUser(String token, UserProfile user);
}
