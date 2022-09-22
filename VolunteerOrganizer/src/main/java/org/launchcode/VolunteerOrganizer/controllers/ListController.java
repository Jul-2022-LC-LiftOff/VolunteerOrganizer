package org.launchcode.VolunteerOrganizer.controllers;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.User;
import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.launchcode.VolunteerOrganizer.models.dto.OpportunityUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller

public class ListController {
    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("list")
    public String list(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        List<String> orgNames = new ArrayList<>();
        List<Opportunity> opportunitiesList = new ArrayList<>();
        Iterable<Opportunity> allOpportunity = opportunityRepository.findAll();
        for(Opportunity opportunity:allOpportunity) {
            if(!orgNames.contains(opportunity.getName())){
                orgNames.add(opportunity.getName());
                opportunitiesList.add(opportunity);
            }
        }
        model.addAttribute("user", user );
        model.addAttribute("opportunities",opportunitiesList );
        return "list";
    }

    @GetMapping("list-opportunity/{orgName}")
    public String listOpportunies(HttpServletRequest request, Model model, @PathVariable String orgName) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        List<Opportunity> opportunity= opportunityRepository.findByName(orgName);
        model.addAttribute("user", user );
        model.addAttribute("heading", "Opportunities for: "+ orgName );
        model.addAttribute("opportunities", opportunity);
        return "list-opportunity";
    }
}
