package org.example.datestore;

import lombok.extern.java.Log;
import org.example.camera.entity.Camera;
import org.example.photo.entity.Photo;
import org.example.user.entity.User;
import org.example.serialization.CloningUtility;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
@ApplicationScoped
public class DateStore {
    private Set<User> users = new HashSet<>();
    private Set<Camera> cameras = new HashSet<>();
    private Set<Photo> photos = new HashSet<>();

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
                .filter(camera -> camera.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized Stream<Camera> getCameraStream() {
        return cameras.stream();
    }

    public void createCamera(Camera camera) {
        if (camera.getId() != null) {
            findCamera(camera.getId()).ifPresent(
                    original -> {
                        throw new IllegalArgumentException(
                                String.format("The camera id \"%s\" is not unique", camera.getId()));
                    }
            );
        }else {
            camera.setId(generateCameraId());
        }
        cameras.add(camera);
    }

    public Optional<Photo> findPhoto(Long id) {
        return photos.stream()
                .filter(photo -> photo.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized Stream<Photo> getPhotoStream() {
        return photos.stream();
    }

    public void createPhoto(Photo photo) {
        photo.setId(generatePhotoId());
        photos.add(photo);
    }

    public void updatePhoto(Photo photo) {
        findPhoto(photo.getId()).ifPresentOrElse(
                original -> {
                    photos.remove(original);
                    photos.add(photo);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The photo with id \"%d\" does not exist", photo.getId()));
                });
    }

    public void updateCamera(Camera camera) {
        System.out.println(camera.getId());

        findCamera(camera.getId()).ifPresentOrElse(
                original -> {
                    cameras.remove(original);
                    cameras.add(camera);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The camera does not exist", camera.getId()));
                });
    }

    public void deleteCamera(Long id) {
        findCamera(id).ifPresentOrElse(
                original -> cameras.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The camera with id \"%d\" does not exist", id));
                });
        deletePhotosTakenByCamera(id);
    }

    private void deletePhotosTakenByCamera(Long id) {
        List<Photo> cameraPhotos = photos.stream()
                .filter(photo -> photo.getCamera().getId().equals(id))
                .collect(Collectors.toList());
        for (Photo photo : cameraPhotos) {
            photos.remove(photo);
        }
    }

    public void deletePhoto(Long id) {
        findPhoto(id).ifPresentOrElse(
                original -> photos.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The photo with id \"%d\" does not exist", id));
                });
    }


    private Long generatePhotoId() {
        return photos.stream()
                .mapToLong(Photo::getId)
                .max().orElse(0) + 1;
    }

    private Long generateCameraId() {
        return cameras.stream()
                .mapToLong(Camera::getId)
                .max().orElse(0) + 1;
    }
}
