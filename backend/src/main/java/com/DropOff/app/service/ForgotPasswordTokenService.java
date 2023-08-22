package com.DropOff.app.service;

import com.DropOff.app.model.User;

import java.security.Key;

public interface ForgotPasswordTokenService {
    public com.DropOff.app.model.ForgotPasswordToken createForgotPasswordToken(User user);

    public Key generateKey();
}
