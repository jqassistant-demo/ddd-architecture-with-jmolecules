package org.jqassistant.demo.architecture.hexagonal.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.jqassistant.demo.architecture.hexagonal.shared.domain.exception.NotFoundException;
import org.jqassistant.demo.architecture.hexagonal.user.domain.UserDomainService;
import org.jqassistant.demo.architecture.hexagonal.user.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public User findById(long id) {
        return userDomainService.findById(id)
            .orElseThrow((() -> new NotFoundException("User not found.")));

    }

    @Transactional
    public User create(User user) {
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
