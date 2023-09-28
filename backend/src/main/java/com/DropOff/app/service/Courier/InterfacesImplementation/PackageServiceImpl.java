package com.DropOff.app.service;

import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.Courier.Package;
import com.DropOff.app.repository.Authentication.UserRepository;
import com.DropOff.app.repository.Courier.PackageRepository;
import com.DropOff.app.service.Authentication.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PackageServiceImpl implements PackageService{

    @Autowired
    PackageRepository packageRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserProfileService userService;

    /**
     * Store package details
     *
     * 
     * @param courier package
     * @return integer stored package id
     */
    public Integer storePackage(Package courier){
        try {
            UserProfile user = userService.getLoggedInUser();

            courier.setSender(user);
            Integer package_id = packageRepo.save(courier).getId();

            return package_id;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save package");
        }
    }

    /**
     * Fetch all the packages of user
     *
     * 
     * @return list of packages
     */
    public List<Package> getPackages(){
        try {
            UserProfile user = userService.getLoggedInUser();

            List<Package> packages = packageRepo.getAllBySender(user);

            return packages;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Fetch all the packages of user
     *
     * 
     * @param courier package
     * @return list of packages
     */
    public String updatePackage(Package courier){
        try{
            Package p = packageRepo.getPackageById(courier.getId());
            float length = courier.getLength();
            float width = courier.getWidth();
            float height = courier.getHeigth();

            p.setTitle(courier.getTitle());
            p.setDescription(courier.getDescription());
            p.setPackageDimension(length,width,height);
            p.setPickup_address(courier.getPickup_address());
            p.setDrop_address(courier.getDrop_address());
            packageRepo.save(p);

            return "Package updated";
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
