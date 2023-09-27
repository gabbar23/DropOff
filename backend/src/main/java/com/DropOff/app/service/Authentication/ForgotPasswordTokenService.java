package com.DropOff.app.service.Authentication;

import java.security.Key;

import com.DropOff.app.model.Authentication.UserProfile;

public interface ForgotPasswordTokenService {
    public com.DropOff.app.model.Authentication.ForgotPasswordToken createForgotPasswordToken(UserProfile user);

    public Key generateKey();
}
