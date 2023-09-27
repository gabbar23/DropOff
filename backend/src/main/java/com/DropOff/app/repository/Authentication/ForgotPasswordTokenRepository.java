package com.DropOff.app.repository.Authentication;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DropOff.app.model.Authentication.ForgotPasswordToken;

public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordToken, Integer> {

    /**
     * findByForgetPasswordToken is a method to get forgot password token by token
     *
     * 
     * @param Forgot_password_token forgot password token
     * @return ForgotPasswordToken object
     */
    ForgotPasswordToken findByForgetPasswordToken(String Forgot_password_token);
}
