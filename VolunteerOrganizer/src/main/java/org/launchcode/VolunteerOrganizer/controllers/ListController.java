package org.launchcode.VolunteerOrganizer.controllers;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller

public class ListController {
    @Autowired
    private OpportunityRepository opportunityRepository;

    @GetMapping("list")
    public String list(Model model) {

        model.addAttribute("opportunities", opportunityRepository.findAll());
        return "list";
    }

    @GetMapping("list-opportunity/{orgId}")
    public String listOpportunies(Model model, @PathVariable int orgId) {

        Optional optOpportunity = opportunityRepository.findById(orgId);

        if (optOpportunity.isPresent()) {
            Opportunity opportunity = (Opportunity) optOpportunity.get();
            model.addAttribute("opportunities", opportunity);
            return "list-opportunity";
        } else {
            //return "redirect:./";
            return "list-opportunity";
        }
    }
}
