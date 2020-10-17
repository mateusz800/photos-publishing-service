package org.example.camera.repository;

import org.example.camera.entity.Camera;
import org.example.datestore.DateStore;
import org.example.repository.Repository;
import org.example.serialization.CloningUtility;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dependent
public class CameraRepository implements Repository<Camera, Long> {
    private DateStore store;

    @Inject
    public CameraRepository(DateStore store){
        this.store = store;
    }

    @Override
    public Optional<Camera> find(Long id) {
       return store.findCamera(id);
    }


    @Override
    public List<Camera> findAll() {
        return store.getCameraStream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    @Override
    public void create(Camera camera) {
        store.createCamera(camera);
    }
}
