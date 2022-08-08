package org.launchcode.VolunteerOrganizer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("create")
public class CreateController {
    @GetMapping
    public String renderCreateOpportunityForm(){
        return "create";
    }
}
