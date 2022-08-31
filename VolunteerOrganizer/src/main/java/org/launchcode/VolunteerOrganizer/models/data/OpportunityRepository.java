package org.launchcode.VolunteerOrganizer.models.data;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.launchcode.VolunteerOrganizer.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityRepository extends CrudRepository<Opportunity, Integer> {
    List<Opportunity> findByName(String orgname);
}
