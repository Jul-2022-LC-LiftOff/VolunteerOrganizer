package org.launchcode.VolunteerOrganizer.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.User;
import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.launchcode.VolunteerOrganizer.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("account")
@Controller
public class AccountController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("")
    public String displayAccount(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("title", "Account Details");
        model.addAttribute("user", user);
        return "account";
    }

    @GetMapping("/delete")
    public String processDeleteAccount(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if (user.getAccountType().equals("organization")) {
            List<Opportunity> opportunities = user.getOpportunitiesForUser(opportunityRepository);
            for (Opportunity opportunity : opportunities) {
                Optional optOpportunity = opportunityRepository.findById(opportunity.getId());
                if (optOpportunity.isPresent()) {
                    Opportunity opportunityToDelete = (Opportunity) optOpportunity.get(); 
                    opportunityRepository.delete(opportunityToDelete);
                }   
            }
        } else if (user.getAccountType().equals("volunteer")) {
            Iterable<Opportunity> allOpportunities = opportunityRepository.findAll();
            for (Opportunity opportunity : allOpportunities) {
                if (opportunity.getVolunteers().contains(user)) {
                    opportunity.removeVolunteer(user);
                }
            }
        }
        session.invalidate();
        userRepository.delete(user);
        return "redirect:/";
    }
}