package com.DropOff.app.repository.Courier;

import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.Courier.Package;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PackageRepository extends JpaRepository<Package,Integer> {

    /**
     * getAllBySender is a method to get all packages by sender
     *
     * 
     * @param sender sender object
     * @return List<Package>
     */
    List<Package> getAllBySender(UserProfile sender);

    /**
     * getAllByReceiver is a method to get all packages by receiver
     *
     * 
     * @param id receiver id
     * @return List<Package>
     */
    Package getPackageById(Integer id);

}
