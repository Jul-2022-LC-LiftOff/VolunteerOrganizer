package org.launchcode.VolunteerOrganizer.models;

import org.launchcode.VolunteerOrganizer.models.data.OpportunityRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String username;
    @NotNull
    private String pwHash;
    private String accountType;
    private String organizationName;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @ManyToMany(mappedBy = "volunteers")
    private final List<Opportunity> opportunities = new ArrayList<>();

    public User() {}

    public User(String username, String pwHash, String accountType, String organizationName) {
        if (accountType.equals("organization")) {
            this.organizationName = organizationName;
        } else {
            this.organizationName = null;
        }
        this.username = username;
        this.pwHash = encoder.encode(pwHash);
        this.accountType = accountType;

    }

    public List<Opportunity> getOpportunitiesForUser(OpportunityRepository opportunityRepository) {
        List<Opportunity> orgOpportunities = new ArrayList<>();
        for(Opportunity opportunity: opportunityRepository.findAll()) {
            if (opportunity.getCreatorUserId() == this.id) {
                orgOpportunities.add(opportunity);
            }
        }
        return orgOpportunities;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAccountType() {
        return accountType;
    }

    public List<Opportunity> getOpportunities() {
        return opportunities;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }
}
