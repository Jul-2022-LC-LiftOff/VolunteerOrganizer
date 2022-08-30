package org.launchcode.VolunteerOrganizer.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

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
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @ManyToMany(mappedBy = "volunteers")
    private final List<Opportunity> opportunities = new ArrayList<>();

    public User() {}

    public User(String username, String password, String accountType) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.accountType = accountType;
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

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }
}
