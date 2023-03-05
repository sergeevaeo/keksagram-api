package edu.keksagram.model;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    Optional<AppUser> findUserById(Long id);

}
