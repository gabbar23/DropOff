package com.DropOff.app.controller.Authentication;

import com.DropOff.app.controller.Authentication.AuthenticationController;
import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.service.*;
import com.DropOff.app.service.Authentication.AuthenticationServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AuthenticationControllerTests {
    @InjectMocks
    AuthenticationController AuthenticationController;
    @Mock
    AuthenticationServiceImpl authService;
    @Mock
    HttpServletRequest request;
    @Mock
    UserProfile user;

    @Test
    public void loginTest() {
        Map<String, String> req = new HashMap<>();
        req.put("email","kadivarnand007@gmail.com");
        req.put("password","abc123");

        AuthenticationController.login(req);

        verify(authService,times(1)).login("kadivarnand007@gmail.com","abc123");
    }

    @Test
    public void registerTest() {
        UserProfile user = new UserProfile();
        user.setFirst_name("Nand");
        user.setLast_name("Kadivar");
        user.setEmail("kadivarnand007@gmailc.om");
        user.setPassword("password123");

        AuthenticationController.registerNewUser(user);

        verify(authService,times(1)).register(user);
    }

    @Test
    public void changePasswordTest() {
        Map<String, String> req = new HashMap<>();
        req.put("token","forgot password token");
        req.put("password","abc123");

        AuthenticationController.changePassword(req);

        verify(authService,times(1)).resetPassword("forgot password token","abc123");
    }

    @Test
    public void forgotPasswordTest() {
        Map<String, String> req = new HashMap<>();
        req.put("email","kadivarnand007@gmail.com");
        when(request.getHeader(any())).thenReturn("");
        AuthenticationController.forgotPassword(request,req);

        verify(authService,times(1)).forgotPassword("kadivarnand007@gmail.com");
    }

    @Test
    public void emailVerificationTest() {
        AuthenticationController.emailVerification("code",1);

        verify(authService,times(1)).verifyEmail("code",1);
    }

    @Test
    public void getUserInfoTest() {
        AuthenticationController.getUserInfo("token");
        verify(authService,times(1)).getUserInfo("token");
    }

    @Test
    public void updateUserTest() {
        AuthenticationController.updateUser("token",user);
        verify(authService,times(1)).updateUser("token",user);
    }
}

