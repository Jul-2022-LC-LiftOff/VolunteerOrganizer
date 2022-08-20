package org.launchcode.VolunteerOrganizer.models;

import java.util.ArrayList;

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

        if (lower_searchTerm.equals("all")) {
            return (ArrayList<Opportunity>) allOpportunities;
        }

        ArrayList<Opportunity> results = new ArrayList<>();

        for (Opportunity opportunity : allOpportunities) {

            if (opportunity.getDescription().toLowerCase().contains(lower_searchTerm)) {
                results.add(opportunity);
            } else if (opportunity.getCity().toString().toLowerCase().contains(lower_searchTerm)) {
                results.add(opportunity);
            } else if (opportunity.getName().toLowerCase().contains(lower_searchTerm)) {
                results.add(opportunity);
            }

        }
        return results;
    }
}