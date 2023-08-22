package com.DropOff.app.service;

import com.DropOff.app.model.Issue;

import java.util.List;

public interface IssueService {
    public String postIssue(Integer package_order_id, String description);

    public List<Issue> getAllIssues();
}
