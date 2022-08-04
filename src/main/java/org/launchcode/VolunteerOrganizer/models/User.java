package org.launchcode.VolunteerOrganizer.models;


public class User {

    private String username;
    private String pwHash;
    private String accountType;

    public User() {}

    public User(String username, String password, String accountType) {
        this.username = username;
        this.pwHash = password;
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public String getAccountType() {
        return accountType;
    }
}
