package org.launchcode.VolunteerOrganizer.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OpportunityData {

    /**
     * Search the city, name and description Opportunity fields for the given term.
     *
     * @param searchTerm       The search term to look for.
     * @param allOpportunities The list of opportunities to search.
     * @return List of all Opportunities with at least one of the city, name or description fields containing the search term.
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

    public static ArrayList<Opportunity> findByCategory(String category, Iterable<Opportunity> allOpportunities) {
        if (category.equals("selectcategory")) {
            return (ArrayList<Opportunity>) allOpportunities;
        }
        ArrayList<Opportunity> results = new ArrayList<>();

        for (Opportunity opportunity : allOpportunities) {
            if (opportunity.getCategory().contains(category)) {
                results.add(opportunity);
            }
        }
        return results;
    }

    public static ArrayList<Opportunity> findByDate(String start, String end, Iterable<Opportunity> allOpportunities) throws ParseException {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<Opportunity> results = new ArrayList<>();

        if (start.equals("") && end.equals("")) {
            return (ArrayList<Opportunity>) allOpportunities;
        }

        if (!start.equals("") && !end.equals("")) {
            Date searchStart = sdformat.parse(start);
            Date searchEnd = sdformat.parse(end);

            for (Opportunity opportunity : allOpportunities) {

                Date oppStart = sdformat.parse(opportunity.getStartDate());
                Date oppEnd = sdformat.parse(opportunity.getEndDate());

                if (oppStart.compareTo(searchStart) >= 0 && oppEnd.compareTo(searchEnd) <= 0) {
                    results.add(opportunity);
                }
            }
        }

        if (!start.equals("") && (end.equals(""))) {
            Date searchStart = sdformat.parse(start);

            for (Opportunity opportunity : allOpportunities) {
                Date oppStart = sdformat.parse(opportunity.getStartDate());

                if (oppStart.compareTo(searchStart) >= 0) {
                    results.add(opportunity);
                }
            }
        }

        if (!end.equals("") && (start.equals(""))) {
            Date searchEnd = sdformat.parse(end);

            for (Opportunity opportunity : allOpportunities) {
                Date oppEnd = sdformat.parse(opportunity.getEndDate());

                if (oppEnd.compareTo(searchEnd) <= 0) {
                    results.add(opportunity);
                }
            }
        }
        return results;
    }
}
