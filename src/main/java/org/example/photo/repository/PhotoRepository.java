package org.example.photo.repository;

import org.example.camera.entity.Camera;
import org.example.datastore.DataStore;
import org.example.photo.entity.Photo;
import org.example.repository.Repository;
import org.example.serialization.CloningUtility;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class PhotoRepository implements Repository<Photo, Long> {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Photo> find(Long id) {
        return Optional.ofNullable(entityManager.find(Photo.class, id));
    }

    @Override
    public List<Photo> findAll() {
        return entityManager.createQuery("select p from Photo p", Photo.class).getResultList();
    }

    @Override
    public void create(Photo entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Photo entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.find(Photo.class, id));
    }

    public List<Photo> findUserPhotos(Long userId) {
        return entityManager
                .createQuery("SELECT p FROM Photo p WHERE  p.user.id = :userId", Photo.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Photo> findCameraPhotos(Long cameraId) {
        return entityManager
                .createQuery("SELECT p FROM Photo p WHERE  p.camera.id = :cameraId", Photo.class)
                .setParameter("cameraId", cameraId)
                .getResultList();
    }
}
