package org.example.camera.service;

import lombok.NoArgsConstructor;
import org.example.camera.entity.Camera;
import org.example.camera.repository.CameraRepository;
import org.example.photo.repository.PhotoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class CameraService {
    private CameraRepository repository;

    @Inject
    public CameraService(CameraRepository repository) {
        this.repository = repository;
    }

    public Optional<Camera> find(Long id) {
        return repository.find(id);
    }
    @Transactional
    public void create(Camera camera) {
        repository.create(camera);

    }

    public List<Camera> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(id);
    }

    @Transactional
    public void update(Camera camera){
        repository.update(camera);
    }
}
