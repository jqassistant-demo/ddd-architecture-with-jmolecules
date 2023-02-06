package org.jqassistant.demo.architecture.hexagonal.user.application.ports.secondary;

import java.util.List;
import java.util.Optional;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.jqassistant.demo.architecture.hexagonal.user.domain.User;

/**
 * Defines the user repository interface.
 */
@SecondaryPort
public interface UserRepository {

    Optional<User> findById(Long id);

    List<User> findAll();

    User save(User user);

    void delete(User user);
}
