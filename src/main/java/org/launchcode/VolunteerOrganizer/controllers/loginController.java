package org.launchcode.VolunteerOrganizer.controllers;

import org.launchcode.VolunteerOrganizer.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class loginController {

    @GetMapping("login")
    public String displayLoginForm() {
        return "loginNew";
    }

    @PostMapping("login")
    @ResponseBody
    public String displayFormData(@RequestParam String username, String password) {

    return "string";
    }
}
