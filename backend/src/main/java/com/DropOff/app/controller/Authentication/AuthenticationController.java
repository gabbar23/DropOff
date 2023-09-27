package com.DropOff.app.controller.Authentication;

import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.service.Authentication.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class AuthenticationController {
    @Autowired
    AuthenticationService authService;

    /**
     * Register user
     
     */
    @PostMapping("/register")
    public String registerNewUser(@RequestBody UserProfile user) {
        return authService.register(user);
    }

    /**
     * User login
     
     */
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> json) {
        long start = System.currentTimeMillis();
        String res = authService.login(json.get("email"), json.get("password"));
        System.out.println("Time difference: "+ (System.currentTimeMillis() - start));
        return res;
    }

    /**
     * Change password

     */
    @PostMapping("/changepassword")
    public String changePassword(@RequestBody Map<String, String> json) {
        return authService.resetPassword(json.get("token"), json.get("password"));
    }

    /**
     * Forgot password

     */
    @PostMapping("/forgotpassword")
    public String forgotPassword(HttpServletRequest request, @RequestBody Map<String, String> json) {
        String origin = request.getHeader(HttpHeaders.ORIGIN);
        return authService.forgotPassword(json.get("email"));
    }

    /**
     * Email verification

     */
    @GetMapping("/verification")
    public String emailVerification(@RequestParam("code") String code, @RequestParam("id") int id) {
        return authService.verifyEmail(code, id);
    }

    /**
     * Email verification
     *

     */
    @GetMapping("/user")
    public String getUser(@RequestParam("id") int id) {
        System.out.println("id = " + id);
        return authService.getUser(id).toString();
    }

    /**
     * User information
     *

     */
    @GetMapping("/user_info")
    public UserProfile getUserInfo(@RequestHeader("Authorization") String token) {
        return authService.getUserInfo(token);
    }

    /**
     * Email verification
     *
     */
    @PutMapping("/user")
    public String updateUser(@RequestHeader("Authorization") String token, @RequestBody UserProfile user) {
        return authService.updateUser(token, user);
    }
}
