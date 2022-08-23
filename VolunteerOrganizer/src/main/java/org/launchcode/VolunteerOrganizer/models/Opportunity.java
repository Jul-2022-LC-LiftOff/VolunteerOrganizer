package org.launchcode.VolunteerOrganizer.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Opportunity extends AbstractEntity{

    @NotBlank(message = "Description is required")
    @Size(min = 5, max = 1000, message = "Description too long or too short!")
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

    public Opportunity(String description, String category, String city, int zipcode, String startDate, String endDate, int hours, String age) {
        this.description = description;
        this.category = category;
        this.city = city;

        this.startDate = startDate;
        this.endDate = endDate;
        this.hours = hours;
        this.age = age;
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

}