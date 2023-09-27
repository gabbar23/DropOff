package com.DropOff.app.service;

import com.DropOff.app.model.Authentication.ForgotPasswordToken;
import com.DropOff.app.model.Authentication.JwtToken;
import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.repository.Authentication.ForgotPasswordTokenRepository;
import com.DropOff.app.service.Authentication.ForgotPasswordTokenServiceImpl;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ForgotPasswordTokenServiceTest {
    @InjectMocks
    ForgotPasswordTokenServiceImpl forgotPasswordTokenService;
    @Mock
    ForgotPasswordTokenRepository forgotPasswordTokenRepo;
    @Mock
    UserProfile user;
    @Test
    public void createForgotPasswordToken(){
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setEmail("kadivarnand007@gmail.com");
        user.setFirst_name("Nand");
        user.setLast_name("Kadivar");
        user.setPassword("abc123");
        ForgotPasswordToken createdToken = forgotPasswordTokenService.createForgotPasswordToken(user);
        assertEquals(user,createdToken.getUser());
    }
}
