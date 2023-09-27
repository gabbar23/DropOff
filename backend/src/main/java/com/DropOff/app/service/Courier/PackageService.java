package com.DropOff.app.service;

import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.Courier.Package;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface PackageService {
    public Integer storePackage(Package courier);

    public List<Package> getPackages();

    public String updatePackage(Package courier);
}
