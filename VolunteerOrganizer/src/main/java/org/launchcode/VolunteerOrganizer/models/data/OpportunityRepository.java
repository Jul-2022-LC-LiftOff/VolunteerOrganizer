package org.launchcode.VolunteerOrganizer.models.data;

import org.launchcode.VolunteerOrganizer.models.Opportunity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends CrudRepository<Opportunity, Integer> {
}
