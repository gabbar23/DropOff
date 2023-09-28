package com.DropOff.app.service;

import com.DropOff.app.model.*;
import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.Courier.Routes;
import com.DropOff.app.model.Courier.Package;
import com.DropOff.app.model.Courier.PackageRequest;
import com.DropOff.app.repository.*;
import com.DropOff.app.repository.Authentication.UserRepository;
import com.DropOff.app.repository.Courier.RoutesRepository;
import com.DropOff.app.repository.Courier.PackageOrderRepository;
import com.DropOff.app.repository.Courier.PackageRepository;
import com.DropOff.app.repository.Courier.PackageRequestRepository;
import com.DropOff.app.service.Authentication.UserProfileServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class PackageRequestServiceImpl implements PackageRequestService {

    @Autowired
    PackageRequestRepository packageRequestRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    PackageRepository packageRepo;

    @Autowired
    PackageOrderRepository packageOrderRepo;

    @Autowired
    PackageOrderService packageOrderService;

    @Autowired
    RoutesRepository driverRouteRepo;

    @Autowired
    UserProfileServiceImpl userService;

    /**
     * Send package request to deliverer route
     *
     * 
     * @param req request
     * @return string response.
     */
    @Override
    public String sendRequest(Map<String,String> req){
        try{
            Integer package_id = Integer.valueOf(req.get("package_id"));
            Long driver_route_id = Long.valueOf(req.get("driver_route_id"));
            int request_count = packageRequestRepo.countAllBy_package_IdAndDriverRoute_Id(package_id,driver_route_id);
            if(packageOrderService.isPackageOrderExist(Integer.valueOf(req.get("package_id")))){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot send request after order creation");
            }

            if(request_count == 0){
                PackageRequest packageRequest = new PackageRequest();
                Routes driverRoute = driverRouteRepo.getDriverRouteById(Long.valueOf(req.get("driver_route_id")));

                UserProfile sender = userService.getLoggedInUser();
                UserProfile deliverer = userRepo.getUserById(Integer.valueOf(driverRoute.getDriverId()));

                packageRequest.setStatus("requested");
                packageRequest.setSender(sender);
                packageRequest.setDeliverer(deliverer);
                packageRequest.setAksPrice(Float.valueOf(req.get("ask_price")));

                packageRequest.setDriverRoute(driverRoute);

                Package p = packageRepo.getPackageById(Integer.valueOf(req.get("package_id")));
                packageRequest.set_package(p);

                boolean isInvalidSenderOrDeliverer = sender==null || deliverer==null;
                boolean isInvalidPackageOrRoute = p==null || driverRoute==null;

                if(isInvalidSenderOrDeliverer || isInvalidPackageOrRoute){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
                }

                packageRequestRepo.save(packageRequest);
            }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already requested");
            }

            return "Request sent";
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Change status of package request
     *
     * 
     * @param package_request_id package request id
     * @param new_status new status that needs to be updated
     */
    private void changeRequestStatus(Integer package_request_id,String new_status){
        PackageRequest packageRequest = packageRequestRepo.getPackageRequestById(package_request_id);
        packageRequest.setStatus(new_status);
        packageRequestRepo.save(packageRequest);
    }

    /**
     * Reject all the request
     *
     * 
     * @param package_id package id
     */
    private void rejectOtherPackageRequests(Integer package_id){
        List<PackageRequest> requests = packageRequestRepo.getAllBy_package_Id(package_id);
        for (PackageRequest req: requests) {
            req.setStatus("rejected");
            packageRequestRepo.save(req);
        }
    }

    /**
     * Accept the request
     *
     * 
     * @param package_request_id package request id
     * @return string response message
     */
    @Override
    public String acceptRequest(Integer package_request_id){
        try{
            PackageRequest packageRequest = packageRequestRepo.getPackageRequestById(package_request_id);

            if (isAbleToAcceptRequest(packageRequest)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot accept request");
            }

            rejectOtherPackageRequests(packageRequest.get_package().getId());
            changeRequestStatus(package_request_id,"accepted");

            String result = packageOrderService.createPackageOrder(packageRequest);

            if(result.equals("order created"))
                return "Request accepted";
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fail to create order");

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Check if request is aligible to accept
     *
     * 
     * @param packageRequest package request
     * @return boolean response
     */
    private boolean isAbleToAcceptRequest(PackageRequest packageRequest){
        boolean isRequestRejected = packageRequest.getStatus().equals("rejected");
        boolean isRequestAccepted = packageRequest.getStatus().equals("accepted");

        return isRequestRejected || isRequestAccepted;
    }

    /**
     * Reject the request
     *
     * 
     * @param package_request_id package request id
     * @return string response
     */
    @Override
    public String rejectRequest(Integer package_request_id){
        try{
            PackageRequest packageRequest = packageRequestRepo.getPackageRequestById(package_request_id);

            if (packageRequest == null || packageRequest.getStatus().equals("rejected")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already rejected");
            }

            changeRequestStatus(package_request_id,"rejected");

            return "Request rejected";
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Delete the package request
     *
     * 
     * @param package_request_id package request id
     * @return string response
     */
    @Override
    public String unsendRequest(Integer package_request_id){
        try{

            PackageRequest packageRequest = packageRequestRepo.getPackageRequestById(package_request_id);

            if (packageRequest == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No request found");
            }

            if(packageRequest.getStatus().equals("accepted")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete accepted request");
            }

            packageRequestRepo.delete(packageRequest);

            return "Request deleted";
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Fetch all requests for deliverer
     *
     * 
     * @return list of package requests
     */
    @Override
    public List<PackageRequest> getRequest(){
        try {
            UserProfile deliverer = userService.getLoggedInUser();

            if (deliverer == null)
                return null;

            return packageRequestRepo.getAllByDeliverer(deliverer);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
