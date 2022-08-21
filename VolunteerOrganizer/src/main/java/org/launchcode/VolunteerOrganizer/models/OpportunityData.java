package org.launchcode.VolunteerOrganizer.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class OpportunityData {

    /**
     * Search all Opportunity fields for the given term.
     *
     * @param searchTerm            The search term to look for.
     * @param allOpportunities The list of opportunities to search.
     * @return List of all Opportunities with at least one field containing the value.
     */
    public static ArrayList<Opportunity> findBySearchTerm(String searchTerm, Iterable<Opportunity> allOpportunities) {
        String lower_searchTerm = searchTerm.toLowerCase();

        if (lower_searchTerm.equals("")) {
            return (ArrayList<Opportunity>) allOpportunities;
        }

        ArrayList<Opportunity> results = new ArrayList<>();

        for (Opportunity opportunity : allOpportunities) {
            if (opportunity.getDescription().toLowerCase().contains(lower_searchTerm)) {
                results.add(opportunity);
            } else if (opportunity.getCity().toLowerCase().contains(lower_searchTerm)) {
                results.add(opportunity);
            } else if (opportunity.getName().toLowerCase().contains(lower_searchTerm)) {
                results.add(opportunity);
            }
        }
        return results;
    }

    public static ArrayList<Opportunity> findByCategory(String category, Iterable<Opportunity>allOpportunities1) {
        if (category.equals("selectcategory")) {
            return (ArrayList<Opportunity>) allOpportunities1;
        }
        ArrayList<Opportunity> results1 = new ArrayList<>();

        for (Opportunity opportunity : allOpportunities1) {
            if (opportunity.getCategory().contains(category)) {
                results1.add(opportunity);
            }
        }
       return results1;
    }

    public static ArrayList<Opportunity> findByDate(String start, String end, Iterable <Opportunity> allOpportunities2) throws ParseException {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdformat.parse(start);
        Date d2 = sdformat.parse(end);


        ArrayList<Opportunity> results2 = new ArrayList<>();

        for (Opportunity opportunity : allOpportunities2) {
            Date d3 = sdformat.parse(opportunity.getStartDate());
            Date d4 = sdformat.parse(opportunity.getEndDate());
            if (d3.compareTo(d1) <= 0 ||  d4.compareTo(d2) >= 0) {
                results2.add(opportunity);
            }
        }
        return results2;
    }
}