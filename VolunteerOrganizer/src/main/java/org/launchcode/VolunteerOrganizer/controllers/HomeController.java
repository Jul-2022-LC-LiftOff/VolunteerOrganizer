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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition.Undefined;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
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
    public String displayHome(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }

    @PostMapping("/results")
    public String displaySearchResults(HttpServletRequest request, Model model, @RequestParam String searchTerm, @RequestParam String category, @RequestParam String start, @RequestParam String end, @RequestParam(required = false) String withVolunteerSlotsAvailable) throws ParseException {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        
        Iterable<Opportunity> opportunities;

        opportunities = OpportunityData.findBySearchTerm(searchTerm, opportunityRepository.findAll());
        opportunities = OpportunityData.findByCategory(category, opportunities);
        opportunities = OpportunityData.findByDate(start, end, opportunities);

        if (withVolunteerSlotsAvailable != null) {
            opportunities = OpportunityData.findByVolunteerSlotsAvailable(withVolunteerSlotsAvailable, opportunities);
        }

        model.addAttribute("title", "Home");
        model.addAttribute("resultsTitle", "Search results:");
        model.addAttribute("opportunities", opportunities);
        model.addAttribute("user", user);

        return "search-results";
    }



    @GetMapping("/volunteer/sign-up")
    public String volunteerSignup(HttpServletRequest request, @RequestParam Integer opportunityId, Model model){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<Opportunity> result = opportunityRepository.findById(opportunityId);

        if(result.isEmpty()) {
            model.addAttribute("title", "Home");
            model.addAttribute("redirectMessageFailure", "Sign Up Unuccessful! Volunteer Opportunity Does Not Exist.");
            return "home";
        }

        Opportunity opportunity = result.get();
        OpportunityUserDTO opportunityVolunteer = new OpportunityUserDTO();
        opportunityVolunteer.setOpportunity(opportunity);

        if (!opportunity.getVolunteers().contains(user)) {
            if (opportunity.getNumVolunteerSlotsRemaining() > 0) {
                opportunity.addVolunteer(user);
                opportunityRepository.save(opportunity);
                model.addAttribute("title", "Home");
                model.addAttribute("redirectMessageSuccess", "Sign Up Successful!");
                return "home";
            } else {
                model.addAttribute("title", "Home");
                model.addAttribute("redirectMessageFailure", "Sign Up Unuccessful! No remaining volunteer slots.");
                return "home";
            }
        } else {
            model.addAttribute("title", "Home");
            model.addAttribute("redirectMessageFailure", "Sign Up Unuccessful! Already registered for this volunteer opportunity.");
            return "home";
        }
    }

    @GetMapping("/redirect/access-denied")
    public String displayHomeRedirectAccessDenied(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("title", "Home");
        model.addAttribute("redirectMessageFailure", "Access Denied as " + user.getAccountType().substring(0, 1).toUpperCase() + user.getAccountType().substring(1) + ": Redirected to Home");
        return "home";
    }

}
