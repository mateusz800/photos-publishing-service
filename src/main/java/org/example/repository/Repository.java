package org.example.repository;

import org.example.User.entity.User;

import java.util.List;
import java.util.Optional;


public interface Repository<E, K> {

    Optional<E> find(K id);
    List<User> findAll();

    void create(E entity);


}
