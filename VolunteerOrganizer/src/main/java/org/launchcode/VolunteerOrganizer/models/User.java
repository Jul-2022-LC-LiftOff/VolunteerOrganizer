package org.launchcode.VolunteerOrganizer.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

// TODO: Extend Abstract Entity
@Entity
public class User {

    @NotNull
    private String username;
    @NotNull
    private String pwHash;
    private String accountType;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {}

    public User(String username, String password, String accountType) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }
}
