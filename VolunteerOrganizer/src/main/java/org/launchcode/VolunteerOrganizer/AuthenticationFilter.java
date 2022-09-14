package org.launchcode.VolunteerOrganizer;

import org.launchcode.VolunteerOrganizer.controllers.AuthenticationController;
import org.launchcode.VolunteerOrganizer.models.User;
import org.launchcode.VolunteerOrganizer.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter implements HandlerInterceptor {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    private static final List<String> whitelist = Arrays.asList("/", "/login", "/logout", "/signup/volunteer", "/signup/organization");
    private static final List<String> organizationUserRequired = Arrays.asList("/create");
    private static final List<String> volunteerUserRequired = Arrays.asList("/home/volunteer");

    private static boolean isWhitelisted(String path) {
        for (String pathRoot : whitelist) {
            if (path.endsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isOrganizationUserRequired(String path) {
        for (String pathRoot : organizationUserRequired) {
            if (path.endsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isVolunteerUserRequired(String path) {
        for (String pathRoot : volunteerUserRequired) {
            if (path.startsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                            throws IOException {

        if (isWhitelisted(request.getRequestURI())) {
            return true;
        }

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if (user != null) {
            if (isOrganizationUserRequired(request.getRequestURI())){
                if (user.getAccountType().equals("organization")) {
                    return true;
                } else {
                    response.sendRedirect("/home/redirect/access-denied");
                    return false;
                }
            } else if (isVolunteerUserRequired(request.getRequestURI())){
                if (user.getAccountType().equals("volunteer")) {
                    return true;
                } else {
                    response.sendRedirect("/home/redirect/access-denied");
                    return false;
                }
            } else {
                return true;
            }
        }
        response.sendRedirect("/");
        return false;
    }
}
