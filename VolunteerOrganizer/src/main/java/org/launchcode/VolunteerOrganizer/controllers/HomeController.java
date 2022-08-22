package org.launchcode.VolunteerOrganizer.controllers;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.OpportunityData;
import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

@RequestMapping("home")
@Controller
public class HomeController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @GetMapping("/")
    public String displayHome(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }

    @PostMapping("/results")
    public String displaySearchResults(Model model, @RequestParam String searchTerm, @RequestParam String category, @RequestParam String start, @RequestParam String end) throws ParseException {
        Iterable<Opportunity> opportunities;

        opportunities = OpportunityData.findBySearchTerm(searchTerm, opportunityRepository.findAll());
        opportunities = OpportunityData.findByCategory(category, opportunities);
        opportunities = OpportunityData.findByDate(start, end, opportunities);

        model.addAttribute("opportunities", opportunities);

        return "home";
    }

}
