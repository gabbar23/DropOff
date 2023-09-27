package com.DropOff.app.service;

import java.util.List;
import java.util.Map;

import com.DropOff.app.model.Courier.PackageRequest;

public interface PackageRequestService {
    public String sendRequest(Map<String,String> req);

    public String acceptRequest(Integer package_request_id);

    public String rejectRequest(Integer package_request_id);

    public String unsendRequest(Integer package_request_id);

    public List<PackageRequest> getRequest();
}
