package org.example.camera.service;

import lombok.NoArgsConstructor;
import org.example.camera.entity.Camera;
import org.example.camera.repository.CameraRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

    public void create(Camera camera) {
        repository.create(camera);
    }

    public List<Camera> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
