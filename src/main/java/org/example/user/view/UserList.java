package org.example.user.view;

import org.example.user.models.UsersModel;
import org.example.user.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class UserList implements Serializable {
    private UserService service;
    private UsersModel users;

    @Inject
    public UserList(UserService service){
        this.service = service;
    }

    public UsersModel getUsers(){
        if(users == null){
            users = UsersModel.entityToDtoMapper().apply(service.findAll());
        }
        return users;
    }
}
