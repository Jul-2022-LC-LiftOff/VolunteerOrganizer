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
import java.util.List;
import java.util.Optional;


@Controller
public class manageController {
    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("manage")
    public String manageOpportunities(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        List<Opportunity> opportunity = user.getOpportunitiesForUser(opportunityRepository);
        model.addAttribute("user", user );
        model.addAttribute("manageOpportunityButtons", true);
        model.addAttribute("opportunities", opportunity);
        return "manage";
    }

    @GetMapping("manage/delete")
    public String deleteOpportunities(HttpServletRequest request,@RequestParam int opportunityId, Model model ) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("user", user );

        Optional optOpportunity = opportunityRepository.findById(opportunityId);
        if (optOpportunity.isPresent()) {
            Opportunity opportunity = (Opportunity) optOpportunity.get();

            opportunityRepository.delete(opportunity);

        }

        return "redirect:/manage";
    }
}
