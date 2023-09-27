package com.DropOff.app.service;

import com.DropOff.app.model.Issue;
import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.Courier.PackageOrder;
import com.DropOff.app.repository.IssueRepository;
import com.DropOff.app.repository.Authentication.UserRepository;
import com.DropOff.app.repository.Courier.PackageOrderRepository;
import com.DropOff.app.service.Authentication.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaiseIssueServiceImpl implements RaiseIssueService {
    @Autowired
    IssueRepository issueRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserProfileService userService;

    @Autowired
    PackageOrderRepository packageOrderRepo;

    /**
     * Post issue
     *
     * 
     * @param package_order_id package order id
     * @param description description
     * @return String
     */
    @Override
    public String postIssue(Integer package_order_id, String description){
        try {
            Issue issue = new Issue();

            UserProfile user = userService.getLoggedInUser();

            Issue currentIssue = issueRepo.getIssueByUser(user);
            boolean isOrderIdMatching = false;
            if(currentIssue!=null)
                isOrderIdMatching = currentIssue.getPackageOrder().getId().equals(package_order_id);
            if(currentIssue!=null && isOrderIdMatching)
                return "Issue Already registered";

            PackageOrder packageOrder = packageOrderRepo.getPackageOrderById(package_order_id);

            issue.setUser(user);
            issue.setPackageOrder(packageOrder);
            issue.setDescription(description);

            issueRepo.save(issue);

        }catch (Exception e){
            return e.getMessage();
        }
        return "Issue registered";
    }

    /**
     * Get all issues
     *
     * 
     * @return List<Issue>
     */
    @Override
    public List<Issue> getAllIssues(){
            return issueRepo.findAll();
    }
}
