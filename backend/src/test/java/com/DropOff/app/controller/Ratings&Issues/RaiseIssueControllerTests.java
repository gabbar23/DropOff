package com.DropOff.app.controller;

import com.DropOff.app.service.RaiseIssueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
public class RaiseIssueControllerTests {
    @InjectMocks
    RaiseIssueController issueController;
    @Mock
    RaiseIssueService issueService;

    @Test
    public void postIssueTest() {
        Map<String, String> req = new HashMap<>();
        req.put("package_order_id","1");
        req.put("description","description");

        issueController.postIssue(req);

        verify(issueService,times(1)).postIssue(1,"description");
    }

    @Test
    public void getAllIssuesTest() {
        issueController.getAllIssues();
        verify(issueService,times(1)).getAllIssues();
    }
}