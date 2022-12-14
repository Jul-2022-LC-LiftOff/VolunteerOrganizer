package org.launchcode.VolunteerOrganizer.controllers;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.User;
import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@RequestMapping("manage")
@Controller
public class ManageController {
    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("")
    public String displayManageOpportunities(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        List<Opportunity> opportunity = user.getOpportunitiesForUser(opportunityRepository);
        model.addAttribute("title", "Manage Volunteer Opportunities");
        model.addAttribute("user", user );
        model.addAttribute("displayManageOpportunityButtons", true);
        model.addAttribute("opportunities", opportunity);
        return "manage";
    }

    @GetMapping("/delete-opportunity")
    public String processDeleteOpportunities(HttpServletRequest request,@RequestParam int opportunityId, Model model ) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("user", user );

        Optional optOpportunity = opportunityRepository.findById(opportunityId);
        if (optOpportunity.isPresent()) {
            Opportunity opportunity = (Opportunity) optOpportunity.get();
            if (user.getId() == opportunity.getCreatorUserId()) {
                opportunityRepository.delete(opportunity);
                return "redirect:";
            } else {
                model.addAttribute("redirectMessageFailure", "You are not the creator of that Volunteer Opportunity! Cannot delete.");
                return "home";
            }
        } else {
            model.addAttribute("redirectMessageFailure", "Unuccessful! Volunteer Opportunity Does Not Exist.");
            return "home";
        }
    }

    @GetMapping("/edit-opportunity")
    public String displayEditOpportunityForm(HttpServletRequest request,@RequestParam int opportunityId, Model model ) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("user", user );

        Optional optOpportunity = opportunityRepository.findById(opportunityId);
        if (optOpportunity.isPresent()) {
            Opportunity opportunity = (Opportunity) optOpportunity.get();
            if (user.getId() == opportunity.getCreatorUserId()) {
                model.addAttribute("title", "Edit Volunteer Opportunity");
                model.addAttribute("opportunity", opportunity);
                return "create";
            } else {
                model.addAttribute("redirectMessageFailure", "You are not the creator of that Volunteer Opportunity! Cannot edit.");
                return "home";
            }
        } else {
            model.addAttribute("redirectMessageFailure", "Unuccessful! Volunteer Opportunity Does Not Exist.");
            return "home";
        }
    }

    @PostMapping("/edit-opportunity")
    public String processEditOpportunityForm(HttpServletRequest request,@ModelAttribute @Valid Opportunity opportunityEdits, Errors errors, @RequestParam int opportunityId, Model model){

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("user", user );
        
        if(errors.hasErrors()) {
            model.addAttribute("title", "Edit Volunteer Opportunity:");
            return "create";
        }

        Optional optOpportunity = opportunityRepository.findById(opportunityId);
        Opportunity opportunity = (Opportunity) optOpportunity.get();

        opportunity.setAge(opportunityEdits.getAge());
        opportunity.setCategory(opportunityEdits.getCategory());  
        opportunity.setCity(opportunityEdits.getCity());
        opportunity.setDescription(opportunityEdits.getDescription());
        opportunity.setEndDate(opportunityEdits.getEndDate());
        opportunity.setHours(opportunityEdits.getHours());
        opportunity.setNumVolunteersNeeded(opportunityEdits.getNumVolunteersNeeded());
        opportunity.setStartDate(opportunityEdits.getStartDate());
      
        opportunityRepository.save(opportunity);
        return "redirect:";
    }
}