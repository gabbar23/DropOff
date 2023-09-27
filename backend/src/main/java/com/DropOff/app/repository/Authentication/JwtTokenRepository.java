package com.DropOff.app.repository.Authentication;

import com.DropOff.app.model.Authentication.JwtToken;
import com.DropOff.app.model.Authentication.UserProfile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JwtTokenRepository extends JpaRepository<JwtToken,Integer> {

    /**
     * getAllByUser is a method to get all jwt tokens by user
     *
     * 
     * @param user user object
     * @return List<JwtToken>
     */
    List<JwtToken> getAllByUser(UserProfile user);

    /**
     * getJwtTokensByToken is a method to get jwt token by token
     *
     * 
     * @param token token
     * @return JwtToken object
     */
    JwtToken getJwtTokensByToken(String token);
}
