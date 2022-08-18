package org.launchcode.VolunteerOrganizer.models.data;

import org.launchcode.VolunteerOrganizer.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
