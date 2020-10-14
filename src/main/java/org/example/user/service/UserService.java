package org.example.user.service;

import lombok.NoArgsConstructor;
import org.example.user.entity.User;
import org.example.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class UserService {
    private UserRepository repository;

    @Inject
    public UserService(UserRepository repository) {
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


    public void removeAvatar(Long id) {
        repository.find(id).ifPresent(user -> {
            user.setAvatar(null);
            repository.update(user);
        });
    }

    public void updateAvatar(Long id, InputStream inputStream) {
        repository.find(id).ifPresent(user -> {
            try {
                user.setAvatar(inputStream.readAllBytes());
                repository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });

    }
}
