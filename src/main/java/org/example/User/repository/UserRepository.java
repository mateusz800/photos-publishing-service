package org.example.User.repository;

import org.example.User.entity.User;
import org.example.datestore.DateStore;
import org.example.repository.Repository;
import org.example.serialization.CloningUtility;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dependent
public class UserRepository implements Repository<User, Long> {

    private DateStore store;

    @Inject
    public UserRepository(DateStore store){
        this.store = store;
    }

    @Override
    public Optional<User> find(Long id) {
        return store.findUser(id);
    }

    @Override
    public List<User> findAll() {
        return store.getUserStream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());

    }

    @Override
    public void create(User entity) {
        store.createUser(entity);
    }
}
