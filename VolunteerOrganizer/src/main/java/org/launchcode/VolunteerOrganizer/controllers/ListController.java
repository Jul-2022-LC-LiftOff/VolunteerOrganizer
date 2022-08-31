package org.launchcode.VolunteerOrganizer.controllers;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.launchcode.VolunteerOrganizer.models.dto.OpportunityUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller

public class ListController {
    @Autowired
    private OpportunityRepository opportunityRepository;

    @GetMapping("list")
    public String list(Model model) {
       // Iterable<Opportunity> allOpportunity = opportunityRepository.findAll();
        model.addAttribute("opportunities", opportunityRepository.findAll());
        return "list";
    }

    @GetMapping("list-opportunity/{orgName}")
    public String listOpportunies( Model model, @PathVariable String orgName) {

        List<Opportunity> opportunity= opportunityRepository.findByName(orgName);

        model.addAttribute("heading", "Opportunities for: "+ orgName );
            model.addAttribute("opportunities", opportunity);
            return "list-opportunity";

    }
}
