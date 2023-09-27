package com.DropOff.app.controller;

import com.DropOff.app.model.Issue;
import com.DropOff.app.service.RaiseIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RaiseIssueController {
    @Autowired
    RaiseIssueService issueService;

    /**
     * Post issue
     *

     * @param req request
     * @return response
     */
    @PostMapping("/issue/post")
    public String postIssue(@RequestBody Map<String,String> req){
        return issueService.postIssue(Integer.valueOf(req.get("package_order_id")),req.get("description"));
    }

    /**
     * Get all issues
     *

     * @return list of issues
     */
    @GetMapping("issue/getall")
    public List<Issue> getAllIssues(){
        return issueService.getAllIssues();
    }
}
