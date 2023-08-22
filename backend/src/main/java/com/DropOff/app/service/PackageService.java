package com.DropOff.app.service;

import com.DropOff.app.model.Package;
import com.DropOff.app.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface PackageService {
    public Integer storePackage(Package courier);

    public List<Package> getPackages();

    public String updatePackage(Package courier);
}
