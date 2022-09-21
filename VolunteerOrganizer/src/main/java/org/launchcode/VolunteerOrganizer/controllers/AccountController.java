package org.launchcode.VolunteerOrganizer.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.VolunteerOrganizer.models.User;
import org.launchcode.VolunteerOrganizer.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("account")
@Controller
public class AccountController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("/delete")
    public String deleteAccount(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        session.invalidate();
        userRepository.delete(user);
        return "redirect:/";
    }
    
}
