package org.jqassistant.demo.architecture.ddd.user.domain;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.jqassistant.demo.architecture.ddd.user.domain.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDomainService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
