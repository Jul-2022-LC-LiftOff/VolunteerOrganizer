package org.launchcode.VolunteerOrganizer.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.launchcode.VolunteerOrganizer.models.OpportunityData;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("search")
public class SearchController {


    @Autowired
    private OpportunityRepository opportunityRepository;

    @RequestMapping("")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        Iterable<Opportunity> opportunities;
        if (searchTerm.toLowerCase().equals("all") || searchTerm.equals("")) {
            opportunities = opportunityRepository.findAll();
        } else {
            opportunities = OpportunityData.findByColumnAndValue(searchType, searchTerm, opportunityRepository.findAll());
        }
        model.addAttribute("columns", columnChoices);
        model.addAttribute("title", "Opportunities with " + columnChoices.get(searchType) + ": " + searchTerm);
        model.addAttribute("opportunities", opportunities);

        return "search";
    }
}


