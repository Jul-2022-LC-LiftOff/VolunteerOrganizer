package org.launchcode.VolunteerOrganizer.models.dto;

import javax.validation.constraints.NotNull;
import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.User;

public class OpportunityUserDTO {

    @NotNull
    private Opportunity opportunity;

    @NotNull
    private User user;

    public OpportunityUserDTO() {}

    public Opportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
