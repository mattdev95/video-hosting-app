package com.mattdev95.videohostingapplication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class WebPageController {

    @PreAuthorize("hasAuthority('CREATOR')")
    @GetMapping("admin")
    public String getAdminDashboard() {
        return "admindashboard";
    }

    @GetMapping("login")
    public String getUserLoginView() {
        return "login";
    }

    @GetMapping("dashboard")
    public String getVideoDashboard() {
        return "videodashboard";
    }


}
