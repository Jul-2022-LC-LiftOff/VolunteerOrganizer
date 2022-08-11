package org.launchcode.VolunteerOrganizer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("create")
public class CreateController {
    private static List<String> orgnames = new ArrayList<>();
    @GetMapping
    public String renderCreateOpportunityForm(Model model){
        orgnames.add("YMCA");
        orgnames.add("Police Department");
        model.addAttribute("orgnames",orgnames);
        return "create";
    }
}
