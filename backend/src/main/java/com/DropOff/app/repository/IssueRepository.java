package com.DropOff.app.repository;

import com.DropOff.app.model.Issue;
import com.DropOff.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

    /**
     * getIssueByUser is a method to get issue by user
     *
     * @author Almasfiza Anwaer Hussain Shaikh
     * @param user user object
     * @return Issue object
     */
    Issue getIssueByUser(User user);
}
