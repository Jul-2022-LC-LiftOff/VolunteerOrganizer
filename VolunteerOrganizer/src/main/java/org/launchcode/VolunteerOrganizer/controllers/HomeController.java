package org.launchcode.VolunteerOrganizer.controllers;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.OpportunityData;
import org.launchcode.VolunteerOrganizer.models.User;
import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.launchcode.VolunteerOrganizer.models.data.UserRepository;
import org.launchcode.VolunteerOrganizer.models.dto.OpportunityUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition.Undefined;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequestMapping("home")
@Controller
public class HomeController {

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private OpportunityRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;


    @GetMapping("")
    public String displayHome(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("user", user);
        return "home";
    }

    @PostMapping("/results")
    public String displaySearchResults(HttpServletRequest request, Model model, @RequestParam String searchTerm, @RequestParam String category, @RequestParam String start, @RequestParam String end, @RequestParam(required = false) String withVolunteerSlotsAvailable) throws ParseException {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        HashMap<String, List<Opportunity>> opportunityByOrganization = new HashMap<>();
        
        Iterable<Opportunity> opportunities;

        opportunities = OpportunityData.findBySearchTerm(searchTerm, opportunityRepository.findAll());
        opportunities = OpportunityData.findByCategory(category, opportunities);
        opportunities = OpportunityData.findByDate(start, end, opportunities);

        if (withVolunteerSlotsAvailable != null) {
            opportunities = OpportunityData.findByVolunteerSlotsAvailable(withVolunteerSlotsAvailable, opportunities);
        }

        for (Opportunity x: opportunities) {
            if (opportunityByOrganization.containsKey(x.getName())) {
                List<Opportunity> orgOpportunities = opportunityByOrganization.get(x.getName());
                orgOpportunities.add(x);
                opportunityByOrganization.put(x.getName(), orgOpportunities );
            } else {
                List<Opportunity> orgOpportunities = new ArrayList<>();
                orgOpportunities.add(x);
                opportunityByOrganization.put(x.getName(),orgOpportunities);
            }
        }

        model.addAttribute("resultsTitle", "Search results:");
        model.addAttribute("opportunities", opportunities);
        model.addAttribute("user", user);
        model.addAttribute("opportunityByOrganization", opportunityByOrganization);

        return "search-results";
    }

    @GetMapping("/search-results-opportunity/{opportunityId}")
    public String searchOpportunityDetail(HttpServletRequest request, Model model, @PathVariable int opportunityId) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("user", user);

        Optional<Opportunity> opportunity = opportunityRepository.findById(opportunityId);
        if(!opportunity.isPresent()){
            model.addAttribute("redirectMessageFailure", "Volunteer Opportunity Does Not Exist.");
            return "home";
        }
        Opportunity x = (Opportunity) opportunity.get();

        model.addAttribute("heading", opportunity.get().getName() +"  "+ opportunity.get().getDescription());
        model.addAttribute("opportunities", x);

        return "search-results-opportunity";
    }

    @GetMapping("/redirect/access-denied")
    public String displayHomeRedirectAccessDenied(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("redirectMessageFailure", "Access Denied as " + user.getAccountType().substring(0, 1).toUpperCase() + user.getAccountType().substring(1) + ": Redirected to Home");
        model.addAttribute("user", user);
        return "home";
    }

}
