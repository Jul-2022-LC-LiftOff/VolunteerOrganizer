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




    @RequestMapping("")
    public String search(Model model) {

        return "search";
    }




}


