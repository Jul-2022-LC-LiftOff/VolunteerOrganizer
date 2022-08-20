package org.launchcode.VolunteerOrganizer.models;

import java.util.ArrayList;

public class OpportunityData {

    /**
     * Returns the results of searching the Opportunity data by field and search term.
     * <p>
     * For example, searching for category "Childcare" will include results
     * with "Childcare".
     *
     * @param column           Opportunity field that should be searched.
     * @param value            Value of the field to search for.
     * @param allOpportunities The list of jobs to search.
     * @return List of all opportunities matching the criteria.
     */
    public static ArrayList<Opportunity> findByColumnAndValue(String column, String value, Iterable<Opportunity> allOpportunities) {

        if (value.toLowerCase().equals("all")) {
            return (ArrayList<Opportunity>) allOpportunities;
        }

        if (column.toLowerCase().equals("all")) {
            return findByValue(value, allOpportunities);
        }

        ArrayList<Opportunity> results = new ArrayList<>();

        for (Opportunity opportunity : allOpportunities) {

            String aValue = getFieldValue(opportunity, column);

            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(opportunity);
            }
        }

        return results;
    }

    public static String getFieldValue(Opportunity opportunity, String fieldName) {
        String theValue = "";
        if (fieldName.equals("description")) {
            theValue = opportunity.getDescription();
        } else if (fieldName.equals("city")) {
            theValue = opportunity.getCity().toString();

        }

        return theValue;
    }

    /**
     * Search all Opportunity fields for the given term.
     *
     * @param value            The search term to look for.
     * @param allOpportunities The list of opportunities to search.
     * @return List of all Opportunities with at least one field containing the value.
     */
    public static ArrayList<Opportunity> findByValue(String value, Iterable<Opportunity> allOpportunities) {
        String lower_val = value.toLowerCase();

        ArrayList<Opportunity> results = new ArrayList<>();

        for (Opportunity opportunity : allOpportunities) {

            if (opportunity.getDescription().toLowerCase().contains(lower_val)) {
                results.add(opportunity);
            } else if (opportunity.getCity().toString().toLowerCase().contains(lower_val)) {
                results.add(opportunity);
            } else if (opportunity.toString().toLowerCase().contains(lower_val)) {
                results.add(opportunity);
            }


        }
        return results;
    }
}