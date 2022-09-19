package org.launchcode.VolunteerOrganizer.controllers;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.User;
import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("volunteer")
@Controller
public class VolunteerController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("/registered-opportunities")
    public String displayRegisteredOpportunities(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Iterable<Opportunity> allOpportunities = opportunityRepository.findAll();
        ArrayList<Opportunity> registeredOpportunities = new ArrayList<>();

        for (Opportunity opportunity : allOpportunities) {
            if (opportunity.getVolunteers().contains(user)) {
                registeredOpportunities.add(opportunity);
            }
        }

        if (!registeredOpportunities.isEmpty()) {
            model.addAttribute("opportunities", registeredOpportunities);
            model.addAttribute("user", user);
            return "registered-opportunities";
        } else {
            model.addAttribute("noOpportunitiesMessage", "You are not currently registered for any volunteer opportunities!");
            return "registered-opportunities";
        }

        // List<Opportunity> opportunity = user.getOpportunitiesForUser(opportunityRepository);
        // model.addAttribute("user", user );

        // model.addAttribute("opportunities", opportunity);
    }

    
    
}
