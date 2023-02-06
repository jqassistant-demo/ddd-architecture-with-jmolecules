package org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.jqassistant.demo.architecture.hexagonal.user.application.ports.primary.UserApplicationService;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.users.UsersApi;
import org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.users.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Implementation of the {@link UsersApi} API using a Spring REST controller.
 */
@PrimaryAdapter
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UsersApiController implements UsersApi {

    private final UserApplicationService userApplicationService;

    private final UsersApiMapper usersApiMapper;

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userApplicationService.findAll()
                .stream()
                .map(usersApiMapper::fromDomain)
                .collect(toList()));
    }

    @Override
    public ResponseEntity<User> createOrUpdateUser(User user) {
        User newUser = usersApiMapper.fromDomain(userApplicationService.save(usersApiMapper.toDomain(user)));
        return ResponseEntity.status(CREATED)
                .body(newUser);
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        return userApplicationService.findById(id)
                .map(user -> ResponseEntity.ok(usersApiMapper.fromDomain(user)))
                .orElse(ResponseEntity.notFound()
                        .build());
    }
}
