package org.launchcode.VolunteerOrganizer.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.launchcode.VolunteerOrganizer.models.OpportunityData;

import java.text.ParseException;
import java.util.ArrayList;


@Controller
@RequestMapping("search")
public class SearchController {


    @Autowired
    private OpportunityRepository opportunityRepository;


    @RequestMapping("")
    public String search(Model model) {

        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchTerm, @RequestParam String category, @RequestParam String start, @RequestParam String end) throws ParseException {
        Iterable<Opportunity> opportunities;

            opportunities = OpportunityData.findBySearchTerm(searchTerm, opportunityRepository.findAll());
            opportunities = OpportunityData.findByCategory(category, opportunities);
            opportunities = OpportunityData.findByDate(start, end, opportunities);


        model.addAttribute("opportunities", opportunities);



        return "search";
    }
}


