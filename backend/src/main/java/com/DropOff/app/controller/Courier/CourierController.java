package com.DropOff.app.controller.Courier;

import com.DropOff.app.model.Courier.Package;
import com.DropOff.app.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourierController {
    @Autowired
    PackageService packageService;

    /**
     * Create package
     *
     */
    @PostMapping("/package/create")
    public Integer createPackage(@RequestBody Package _package){
        return packageService.storePackage(_package);
    }

    /**
     * Get all packages
     *
     */
    @GetMapping("/package/getall")
    public List<Package> getAllPackages(){
        return packageService.getPackages();
    }

    /**
     * Update package
     *
     */
    @PutMapping("/package/update")
    public String updatePackage(@RequestBody Package _package){
        return packageService.updatePackage(_package);
    }
}
