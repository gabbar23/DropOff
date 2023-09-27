package com.DropOff.app.repository.Courier;

import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.Courier.PackageOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageOrderRepository extends JpaRepository<PackageOrder,Integer> {

    /**
     * getPackageOrderById is a method to get package order by id
     *
     * 
     * @param package_order_id package order id
     * @return PackageOrder object
     */
    PackageOrder getPackageOrderById(Integer package_order_id);

    /**
     * getBy_package_Id is a method to get package order by package id
     *
     * 
     * @param package_id package id
     * @return PackageOrder object
     */
    PackageOrder getBy_package_Id(Integer package_id);

    /**
     * getAllBySender is a method to get all package orders by sender
     *
     * 
     * @param user user object
     * @return List<PackageOrder>
     */
    List<PackageOrder> getAllBySender(UserProfile user);

    /**
     * getAllByReceiver is a method to get all package orders by receiver
     *
     * 
     * @param deliver_route_id deliver route id
     * @return List<PackageOrder>
     */
    List<PackageOrder> getAllByDriverRoute_Id(Long deliver_route_id);
}