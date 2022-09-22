package org.launchcode.VolunteerOrganizer.controllers;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.User;
import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("create")
public class CreateController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("")
    public String renderCreateOpportunityForm(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("title", "Create Volunteer Opportunity:");
        model.addAttribute("opportunity", new Opportunity());
        model.addAttribute("user", user);
        return "create";
    }

    @PostMapping("")
    public String processCreateOpportunityForm(HttpServletRequest request,@ModelAttribute @Valid Opportunity opportunity, Errors errors, Model model){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        opportunity.setCreatorUserId(user.getId());
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Volunteer Opportunity:");
            return "create";
        }
        opportunity.setName(user.getOrganizationName());
        opportunityRepository.save(opportunity);
        return "redirect:/home";
    }
}