package com.sportalytics.controller.main;

import com.sportalytics.model.Users;
import com.sportalytics.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

public class Main {

    @Autowired
    protected UsersRepo usersRepo;
    @Autowired
    protected OpinionsRepo opinionsRepo;
    @Autowired
    protected MatchesRepo matchesRepo;
    @Autowired
    protected MatchStatsRepo matchStatsRepo;
    @Autowired
    protected TeamsRepo teamsRepo;
    @Autowired
    protected TeamStatsRepo teamStatsRepo;
    @Autowired
    protected MarksRepo marksRepo;
    @Value("${upload.img}")
    protected String uploadImg;

    protected Users getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return usersRepo.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected String getRole() {
        Users users = getUser();
        if (users == null) return "NOT";
        return users.getRole().toString();
    }

    protected String DateNow() {
        return LocalDateTime.now().toString().substring(0, 10);
    }

    protected DecimalFormat decimalFormat = new DecimalFormat("0.00");
}