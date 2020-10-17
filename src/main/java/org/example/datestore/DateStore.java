package org.example.datestore;

import lombok.extern.java.Log;
import org.example.camera.entity.Camera;
import org.example.user.entity.User;
import org.example.serialization.CloningUtility;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Log
@ApplicationScoped
public class DateStore {
    private Set<User> users = new HashSet<>();
    private Set<Camera> cameras = new HashSet<>();

    public synchronized Optional<User> findUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createUser(User user) throws IllegalArgumentException {
        findUser(user.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The user id \"%s\" is not unique", user.getId()));
                },
            () -> users.add(user));
    }

    public Stream<User> getUserStream() {
        return users.stream();
    }

    public void updateUser(User user) {
        findUser(user.getId()).ifPresentOrElse(
                original -> {
                    users.remove(original);
                    users.add(user);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The character with id \"%d\" does not exist", user.getId()));
                });

    }

    public synchronized Optional<Camera> findCamera(Long id) {
        return cameras.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized Stream<Camera> getCameraStream() {
        return cameras.stream();
    }

    public void createCamera(Camera camera) {
        findUser(camera.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The user id \"%s\" is not unique", camera.getId()));
                },
                () -> cameras.add(camera));
    }
}
