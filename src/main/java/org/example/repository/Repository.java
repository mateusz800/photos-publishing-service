package org.example.repository;

import org.example.camera.entity.Camera;
import org.example.user.entity.User;

import java.util.List;
import java.util.Optional;


public interface Repository<E, K> {

    Optional<E> find(K id);
    List<E> findAll();

    void create(E entity);
}
