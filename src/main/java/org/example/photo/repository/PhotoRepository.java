package org.example.photo.repository;

import org.example.datestore.DateStore;
import org.example.photo.entity.Photo;
import org.example.repository.Repository;
import org.example.serialization.CloningUtility;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dependent
public class PhotoRepository implements Repository<Photo, Long> {
    private DateStore store;

    @Inject
    public PhotoRepository(DateStore store){
        this.store = store;
    }

    @Override
    public Optional<Photo> find(Long id) {
        return store.findPhoto(id);
    }

    @Override
    public List<Photo> findAll() {
        return store.getPhotoStream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    @Override
    public void create(Photo entity) {
        store.createPhoto(entity);
    }

    @Override
    public void update(Photo entity) {
        store.updatePhoto(entity);
    }

    @Override
    public void delete(Long id) {
        store.deletePhoto(id);
    }

    public List<Photo> findUserPhotos(Long userId) {
        return store.getPhotoStream()
                .filter(photo -> photo.getAuthor().getId().equals(userId))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public List<Photo> findCameraPhotos(Long id) {
        return store.getPhotoStream()
                .filter(photo -> photo.getCamera().getId().equals(id))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }
}
