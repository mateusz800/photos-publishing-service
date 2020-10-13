package org.example.User.service;

import lombok.NoArgsConstructor;
import org.example.User.entity.User;
import org.example.User.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class UserService {
    private UserRepository repository;

    @Inject
    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public Optional<User> find(Long id) {
        return repository.find(id);
    }

    public void create(User user) {
        repository.create(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
