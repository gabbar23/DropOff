package com.DropOff.app.service.Authentication;

import com.DropOff.app.model.Authentication.JwtToken;
import com.DropOff.app.model.Authentication.UserProfile;

import java.security.Key;

public interface JwtTokenService {
    public JwtToken createJwtToken(UserProfile user);

    public Key generateKey();

    public void deactiveUserTokens(UserProfile user);

    public boolean isJwtActive(String token);
}
