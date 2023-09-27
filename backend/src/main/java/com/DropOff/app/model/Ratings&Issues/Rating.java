package com.DropOff.app.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.Courier.DropOffRoutes;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Rating model.
 *

 */
@Entity
public class Rating {
    @Id
    @GeneratedValue
    @Column(name = "rating_id")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private UserProfile user;

    @OneToOne
    @JoinColumn(name="driver_route_id",referencedColumnName = "id")
    private DropOffRoutes driverRoute;

    @Column(name = "star")
    private Float star;

    @Column(name = "review")
    private String review;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public DropOffRoutes getDriverRoute() {
        return driverRoute;
    }

    public void setDriverRoute(DropOffRoutes driverRoute) {
        this.driverRoute = driverRoute;
    }

    public Float getStar() {
        return star;
    }

    public void setStar(Float star) {
        this.star = star;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
