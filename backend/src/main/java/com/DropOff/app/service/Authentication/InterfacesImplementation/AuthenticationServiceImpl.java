package com.DropOff.app.service.Authentication;

import com.DropOff.app.model.Authentication.ForgotPasswordToken;
import com.DropOff.app.model.Authentication.JwtToken;
import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.repository.Authentication.ForgotPasswordTokenRepository;
import com.DropOff.app.repository.Authentication.JwtTokenRepository;
import com.DropOff.app.repository.Authentication.UserRepository;
import com.DropOff.app.service.MailServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.DropOff.app.utility.Const.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Autowired
    UserRepository userRepo;
    @Autowired
    JwtTokenRepository jwtTokenRepo;
    @Autowired
    JwtTokenService jwtTokenService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    MailServiceImpl mailService;
    @Autowired
    ForgotPasswordTokenRepository forgotPasswordTokenRepo;
    @Autowired
    ForgotPasswordTokenService forgotPasswordTokenService;

    /**
     * Check that user is already registered on not
     *
     * 
     * @param user user.
     * @return boolean value.
     */
    @Override
    public boolean isAlreadyExist(UserProfile user) {
        UserProfile db_user = userRepo.findUserByEmail(user.getEmail());
        return db_user != null;
    }

    /**
     * Reset password for user
     *
     * 
     * @param token reset password token.
     * @param password new password string.
     * @return String message.
     */
    @Override
    public String resetPassword(String token, String password) {
        try {
            ForgotPasswordToken forgotPasswordToken = forgotPasswordTokenRepo.findByForgetPasswordToken(token);
            if (forgotPasswordToken.getIs_active()) {
                Claims claim = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
                String email = (String) claim.get("email");

                UserProfile user = userRepo.getUserByEmail(email);
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String new_password = encoder.encode(password);
                user.setPassword(new_password);
                userRepo.save(user);
                forgotPasswordToken.setIs_active(false);
                forgotPasswordTokenRepo.save(forgotPasswordToken);

                return "Password changed successfully";
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Link is not active");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Send reset password email to user
     *
     * 
     * @param email email string.
     * @return String response message.
     */
    @Override
    public String forgotPassword(String email) {
        try {
            UserProfile user = userRepo.getUserByEmail(email);

            ForgotPasswordToken token = forgotPasswordTokenService.createForgotPasswordToken(user);
            String forgot_password_token = token.getForgot_password_token();

            String user_email = user.getEmail();
            String subject = "Reset Password";
            String body = "Password rest link(Expires in 24 hours): ";
            String reset_link =  URL_FRONTEND+"/forgotpwd/reset/"+forgot_password_token;
            mailService.sendMail(user_email, subject, body, reset_link);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "Password reset link sent";
    }

    /**
     * Verify user email
     *
     * 
     * @param code verification code.
     * @param id user id.
     * @return String response message.
     */
    @Override
    public String verifyEmail(String code, int id) {

        try {
            UserProfile user = userRepo.getById(id);

            if (!user.getIs_verified()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                if (encoder.matches(user.getEmail(), code)) {
                    user.setIs_verified(true);
                    userRepo.save(user);
                    return "Email Verified";
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not valid user");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not valid user");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Register user and send verification email
     *
     * 
     * @param new_user user.
     * @return String response message.
     */
    @Override
    public String register(UserProfile new_user) {
        if (!isAlreadyExist(new_user)) {
            String user_password = new_user.getPassword();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            new_user.setPassword(encoder.encode(user_password));
            new_user.setIs_verified(false);
            userRepo.save(new_user);

            String encoded_email = encoder.encode(new_user.getEmail());

            String user_email = new_user.getEmail();
            String subject = "Email Verification";
            String body = "Please verify your email:";
            String link =  URL_BACKEND+"/verification?code="+encoded_email+"&id="+new_user.getUser_id();

            mailService.sendMail(user_email,subject, body, link);

            return "Verification email sent";

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already exist with this email");
        }
    }

    /**
     * User login
     *
     * 
     * @param email user email.
     * @param password user password.
     * @return String jwt token for that user.
     */
    @Override
    public String login(String email, String password) {

        UsernamePasswordAuthenticationToken auth_token = new UsernamePasswordAuthenticationToken(email, password);
        System.out.println(auth_token);
        try {
            authManager.authenticate(auth_token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        UserProfile storedUser = userRepo.getUserByEmail(email);

        if (storedUser.getIs_verified()) {
            JwtToken token = jwtTokenService.createJwtToken(storedUser);
            jwtTokenService.deactiveUserTokens(storedUser);
            jwtTokenRepo.save(token);
            return token.getToken();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not verified");
        }
    }

    /**
     * get user by id
     *
     * 
     * @param id user id.
     * @return boolean true if user already exist.
     */
    @Override
    public UserProfile getUser(int id) {
        return userRepo.getReferenceById(id);
    }

    /**
     * get user by token
     *
     * 
     * @param token jwt token.
     * @return boolean true if user already exist.
     */
    @Override
    public UserProfile getUserInfo(String token) {
        token = token.replace("Bearer", "").trim();
        Claims claim = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        String email = (String) claim.get("email");
        return userRepo.getUserByEmail(email);
    }

    /**
     * update user by token
     *
     * 
     * @param user user object.
     * @return boolean true if user already exist.
     */
    @Override
    public String updateUser(String token, UserProfile user) {
        token = token.replace("Bearer", "").trim();
        Claims claim = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        String email = (String) claim.get("email");
        UserProfile db_user = userRepo.getUserByEmail(email);
        db_user.update(user);
        userRepo.save(db_user);
        return "User updated";
    }
}
