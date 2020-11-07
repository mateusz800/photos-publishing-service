package org.example.camera.repository;

import org.example.camera.entity.Camera;
import org.example.repository.Repository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class CameraRepository implements Repository<Camera, Long> {
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<Camera> find(Long id) {
       return Optional.ofNullable(entityManager.find(Camera.class, id));
    }


    @Override
    public List<Camera> findAll() {
        return entityManager.createQuery("select c from Camera c", Camera.class).getResultList();
    }

    @Override
    public void create(Camera camera) {
        entityManager.persist(camera);
    }

    @Override
    public void update(Camera entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.find(Camera.class, id));
    }
}
