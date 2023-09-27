package com.DropOff.app.model.Authentication;

import javax.persistence.*;

/**
 * Jwt token model.
 *
 * 
 */
@Entity
@Table(name="jwtToken")
public class JwtToken {
    @Id
    @GeneratedValue
    @Column(name="token_id")
    private Integer token_id;

    @Column(name="token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID",referencedColumnName = "user_id")
    private UserProfile user;

    @Column(name="is_active")
    private Boolean is_active;

}
