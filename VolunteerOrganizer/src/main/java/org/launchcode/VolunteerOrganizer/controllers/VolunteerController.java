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
import java.util.Optional;

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
            return "registered-opportunities";
        }
    }

    @GetMapping("/sign-up")
    public String volunteerSignup(HttpServletRequest request, @RequestParam Integer opportunityId, Model model){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<Opportunity> result = opportunityRepository.findById(opportunityId);

        if(result.isEmpty()) {
            model.addAttribute("title", "Home");
            model.addAttribute("redirectMessageFailure", "Sign Up Unuccessful! Volunteer Opportunity Does Not Exist.");
            return "home";
        }

        Opportunity opportunity = result.get();
        OpportunityUserDTO opportunityVolunteer = new OpportunityUserDTO();
        opportunityVolunteer.setOpportunity(opportunity);

        if (!opportunity.getVolunteers().contains(user)) {
            if (opportunity.getNumVolunteerSlotsRemaining() > 0) {
                opportunity.addVolunteer(user);
                opportunityRepository.save(opportunity);
                return "redirect:./registered-opportunities";
            } else {
                model.addAttribute("title", "Home");
                model.addAttribute("redirectMessageFailure", "Sign Up Unuccessful! No remaining volunteer slots.");
                return "home";
            }
        } else {
            model.addAttribute("title", "Home");
            model.addAttribute("redirectMessageFailure", "Sign Up Unuccessful! Already registered for this volunteer opportunity.");
            return "home";
        }
    }
    
    @GetMapping("/unregister")
    public String volunteerUnregister(HttpServletRequest request, @RequestParam Integer opportunityId, Model model){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<Opportunity> result = opportunityRepository.findById(opportunityId);

        if(result.isEmpty()) {
            model.addAttribute("title", "Home");
            model.addAttribute("redirectMessageFailure", "Unuccessful! Volunteer Opportunity Does Not Exist.");
            return "home";
        }

        Opportunity opportunity = result.get();
        OpportunityUserDTO opportunityVolunteer = new OpportunityUserDTO();
        opportunityVolunteer.setOpportunity(opportunity);

        if (opportunity.getVolunteers().contains(user)) {
            opportunity.removeVolunteer(user);
            opportunityRepository.save(opportunity);
            return "redirect:./registered-opportunities";
        } else {
            model.addAttribute("title", "Home");
            model.addAttribute("redirectMessageFailure", "You are not registered for this volunteer opportunity! Cannot unregister.");
            return "home";
        }
    }
    
}
