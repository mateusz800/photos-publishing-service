package org.example.photo.service;

import lombok.NoArgsConstructor;
import org.example.camera.repository.CameraRepository;
import org.example.photo.entity.Photo;
import org.example.photo.repository.PhotoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class PhotoService {
    private PhotoRepository repository;
    private CameraRepository cameraRepository;

    @Inject
    public PhotoService(PhotoRepository repository, CameraRepository cameraRepository) {
        this.repository = repository;
        this.cameraRepository = cameraRepository;
    }

    public Optional<Photo> find(Long id) {
        return repository.find(id);
    }

    @Transactional
    public void create(Photo photo) {
        repository.create(photo);
        cameraRepository.find(photo.getCamera().getId()).ifPresent(user -> user.getPhotos().add(photo));
    }

    public List<Photo> findAll() {
        return repository.findAll();
    }

    public List<Photo> findUserPhotos(Long userId) {
        return repository.findUserPhotos(userId);
    }

    @Transactional
    public void update(Photo photo) {
        repository.update(photo);
    }

    @Transactional
    public void delete(Long id) {
        Photo photo = repository.find(id).orElseThrow();
        photo.getAuthor().getPhotos().remove(photo);
        photo.getCamera().getPhotos().remove(photo);
        repository.delete(id);

    }

    public List<Photo> findCameraPhotos(Long id) {
        return repository.findCameraPhotos(id);
    }
}
