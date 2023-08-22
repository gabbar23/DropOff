package com.DropOff.app.repository;

import com.DropOff.app.model.Package;
import com.DropOff.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PackageRepository extends JpaRepository<Package,Integer> {

    /**
     * getAllBySender is a method to get all packages by sender
     *
     * @author Nandkumar Kadivar
     * @param sender sender object
     * @return List<Package>
     */
    List<Package> getAllBySender(User sender);

    /**
     * getAllByReceiver is a method to get all packages by receiver
     *
     * @author Nandkumar Kadivar
     * @param id receiver id
     * @return List<Package>
     */
    Package getPackageById(Integer id);

}
