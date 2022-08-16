package org.launchcode.VolunteerOrganizer.controllers;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("create")
public class CreateController {
    private static List<String> orgnames = new ArrayList<>();

    @Autowired
    private OpportunityRepository opportunityRepository;

    @GetMapping
    public String renderCreateOpportunityForm(Model model){
        orgnames.add("YMCA");
        orgnames.add("Police Department");
        model.addAttribute("orgnames",orgnames);
        return "create";
    }

    @PostMapping
    public String processCreateOpportunityForm(@ModelAttribute @Valid Opportunity opportunity, Model model,Errors errors)
                                              {
      if(errors.hasErrors()) {
         // model.addAttribute("errorMsg", "Data provided is incorrect!");
          return "redirect:";
      }
        opportunityRepository.save(opportunity);
        return "redirect:";
    }



}
