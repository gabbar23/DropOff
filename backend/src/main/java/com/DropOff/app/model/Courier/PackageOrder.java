package com.DropOff.app.model.Courier;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.DropOff.app.model.Authentication.UserProfile;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * PackageOrder model.
 *
 * 
 */
@Entity
public class PackageOrder {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    Integer id;

    @OneToOne
    @JoinColumn(name="package_id",referencedColumnName = "package_id")
    private Package _package;

    @ManyToOne
    @JoinColumn(name = "sender_id",referencedColumnName = "user_id")
    private UserProfile sender;

    @ManyToOne
    @JoinColumn(name = "deliverer_id",referencedColumnName = "user_id")
    private UserProfile deliverer;

    @ManyToOne
    @JoinColumn(name="driver_route_id",referencedColumnName = "id")
    private Routes driverRoute;

    @Column(name="pickup_code")
    private Integer pickup_code;

    @Column(name="drop_code")
    private Integer drop_code;

    @Column(name="isStarted")
    private boolean isStarted;

    @Column(name="isDelivered")
    private boolean isDelivered;

    @Column(name="isCanceled")
    private boolean isCanceled;

    @Column(name="paymentStatus")
    private Integer paymentStatus;

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

    public void set_package(Package _package) {
        this._package = _package;
    }

    public UserProfile getSender() {
        return sender;
    }

    public void setSender(UserProfile sender) {
        this.sender = sender;
    }

    public void setDeliverer(UserProfile deliverer) {
        this.deliverer = deliverer;
    }

    public void setDriverRoute(Routes driverRoute) {
        this.driverRoute = driverRoute;
    }

    public Integer getPickup_code() {
        return pickup_code;
    }

    public void setPickup_code(Integer pickup_code) {
        this.pickup_code = pickup_code;
    }

    public Integer getDrop_code() {
        return drop_code;
    }

    public void setDrop_code(Integer drop_code) {
        this.drop_code = drop_code;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Package get_package() {
        return _package;
    }

    public UserProfile getDeliverer() {
        return deliverer;
    }

    public Routes getDriverRoute() {
        return driverRoute;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
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
