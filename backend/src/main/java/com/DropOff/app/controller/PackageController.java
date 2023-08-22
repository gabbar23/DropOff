package com.DropOff.app.controller;

import com.DropOff.app.model.Package;
import com.DropOff.app.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PackageController {
    @Autowired
    PackageService packageService;

    /**
     * Create package
     *
     * @author Nandkumar Kadivar
     * @param _package Package object.
     * @return integer package id.
     */
    @PostMapping("/package/create")
    public Integer createPackage(@RequestBody Package _package){
        return packageService.storePackage(_package);
    }

    /**
     * Get all packages
     *
     * @author Nandkumar Kadivar
     * @return List packages.
     */
    @GetMapping("/package/getall")
    public List<Package> getAllPackages(){
        return packageService.getPackages();
    }

    /**
     * Update package
     *
     * @author Nandkumar Kadivar
     * @param _package package object
     * @return string response.
     */
    @PutMapping("/package/update")
    public String updatePackage(@RequestBody Package _package){
        return packageService.updatePackage(_package);
    }
}
