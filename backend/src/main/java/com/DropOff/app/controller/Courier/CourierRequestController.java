package com.DropOff.app.controller.Courier;

import com.DropOff.app.model.Courier.PackageRequest;
import com.DropOff.app.service.PackageRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CourierRequestController {
    @Autowired
    PackageRequestService packageRequestService;

    /**
     * Send package request
     *
     */
    @PostMapping("package/request/send")
    public String sendPackageRequest(@RequestBody Map<String,String> req){
        return packageRequestService.sendRequest(req);
    }

    /**
     * Get all package request

     */
    @GetMapping("package/request/getall")
    public List<PackageRequest> getPackageRequest(){
        return packageRequestService.getRequest();
    }

    /**
     * Accept package request
     *
     */
    @PostMapping("package/request/accept")
    public String acceptPackageRequest(@RequestBody Map<String,String> req){
        return packageRequestService.acceptRequest(Integer.parseInt(req.get("package_request_id")));
    }

    /**
     * Send package request
     *
     */
    @PostMapping("package/request/reject")
    public String rejectPackageRequest(@RequestBody Map<String,String> req){
        return packageRequestService.rejectRequest(Integer.parseInt(req.get("package_request_id")));
    }

    /**
     * Delete package request
     */
    @DeleteMapping("package/request/delete")
    public String unsendPackageRequest(@RequestBody Map<String,String> req){
        return packageRequestService.unsendRequest(Integer.parseInt(req.get("package_request_id")));
    }
}
