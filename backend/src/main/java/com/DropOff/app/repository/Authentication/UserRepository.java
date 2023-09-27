package com.DropOff.app.repository.Authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.DropOff.app.model.Authentication.UserProfile;

import java.util.List;

public interface UserRepository extends JpaRepository<UserProfile, Integer> {

    /**
     * getUserByEmail is a method to get user by email
     *
     * 
     * @param email email
     * @return User object
     */
    UserProfile getUserByEmail(String email);

    /**
     * findUserByEmail is a method to find user by email
     *
     * 
     * @param email email
     * @return User object
     */
    UserProfile findUserByEmail(String email);

    /**
     * findByEmail is a method to find user by email
     *
     * 
     * @param email email
     * @return User object
     */
    UserProfile findByEmail(String email);

    /**
     * getUserById is a method to get user by id
     *
     * 
     * @param id user id
     * @return User object
     */
    UserProfile getUserById(Integer id);

    /**
     * getUserByIds is a method to get user by ids
     *
     * 
     * @param userIds user ids
     * @return List<User>
     */
    @Query("SELECT u FROM User u WHERE u.id in :userIds")
    List<UserProfile> getUserByIds(@Param("userIds") List<Integer> userIds);

}
