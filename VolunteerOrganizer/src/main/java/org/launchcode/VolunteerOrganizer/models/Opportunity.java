package org.launchcode.VolunteerOrganizer.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Opportunity extends AbstractEntity{

//    @NotBlank(message = "Name is required")
//    @Size(min = 2, max = 50, message = "Name must be between 3 and 50 characters")
//    private String orgName;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000, message = "Description too long or too short!")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "City is required")
    private String city;

    @NotNull(message = "Zipcode is required")
    private int zipcode;

    @NotNull(message = "Start Date is required")
    private String startDate;

    @NotNull(message = "End Date is required")
    private String endDate;

    @NotNull(message = "Hours is required")
    private int hours;

    @NotBlank(message = "Age Group is required")
    private String age;

//   @ManyToOne
//   private User user;

    public Opportunity(String description, String category, String city, int zipcode, String startDate, String endDate, int hours, String age) {
        //this.name = name;
        this.description = description;
        this.category = category;
        this.city = city;
        this.zipcode = zipcode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hours = hours;
        this.age = age;
        //this.user = user;
    }

    public Opportunity() {
    }

//    public int getId() {
//        return id;
//    }

//    public String getName() {
//        return orgName;
//    }
//
//    public void setName(String name) {
//        this.orgName = name;
//    }

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

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
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

//    public User getUser() {
//        return user;
//    }

//    public void setUser(User user) {
//        this.user = user;
//    }
}