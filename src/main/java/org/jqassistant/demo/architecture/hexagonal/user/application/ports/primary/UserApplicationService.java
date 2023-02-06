package org.jqassistant.demo.architecture.hexagonal.user.application.ports.primary;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.jqassistant.demo.architecture.hexagonal.user.application.UserDomainService;
import org.jqassistant.demo.architecture.hexagonal.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User related application layer service.
 */
@PrimaryPort
@Service
@Slf4j
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserDomainService userDomainService;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDomainService.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {
        return userDomainService.findById(id);
    }

    @Transactional
    public User save(User user) {
        log.info("Saving user {}.", user);
        return userDomainService.save(user);
    }

    @Transactional
    public void delete(long id) {
        log.info("Deleting user with id {}.", id);
        userDomainService.findById(id)
                .ifPresent(userDomainService::delete);
    }
}
