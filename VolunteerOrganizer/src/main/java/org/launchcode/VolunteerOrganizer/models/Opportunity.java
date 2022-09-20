package org.launchcode.VolunteerOrganizer.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Entity
@NamedQuery(name = "Opportunity.findByName", query = "select s from Opportunity s where s.name = ?1")
public class Opportunity extends AbstractEntity{

    private int creatorUserId;
    @NotBlank(message = "Description is required")
    @Size(min = 5, max = 1000, message = "Description too long or too short! Must be between 5 and 1,000 characters in length")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Start Date is required")
    private String startDate;

    @NotBlank(message = "End Date is required")
    private String endDate;

    @Min(value=1,message = "Enter valid number of hours")
    private int hours;

    @NotBlank(message = "Age Group is required")
    private String age;

    @Positive(message = "Number must be greater than 0")
    @Digits(integer = 6, fraction = 0, message = "Must be a whole number")
    private int numVolunteersNeeded;

    @ManyToMany
    private final List<User> volunteers = new ArrayList<>();


    public Opportunity(String description, String category, String city, int zipcode, String startDate, String endDate, int hours, String age, int numVolunteersNeeded, int creatorUserID) {
        this.description = description;
        this.category = category;
        this.city = city;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hours = hours;
        this.age = age;
        this.numVolunteersNeeded = numVolunteersNeeded;
        this.creatorUserId = creatorUserId;
    }

    public Opportunity() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<User> getVolunteers() {
        return volunteers;
    }

    public int getNumVolunteersNeeded() {
        return numVolunteersNeeded;
    }

    public void setNumVolunteersNeeded(int volunteersNeeded) {
        this.numVolunteersNeeded = volunteersNeeded;
    }

    public int getNumVolunteerSlotsRemaining() {
        return numVolunteersNeeded - volunteers.size();
    }

    public void addVolunteer(User volunteer) {
        this.volunteers.add(volunteer);
    }

    public void removeVolunteer(User volunteer) {
        this.volunteers.remove(volunteer);
    }

    public int getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(int creatorUserId) {
        this.creatorUserId = creatorUserId;
    }
}