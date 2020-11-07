package org.example.photo.models.converter;

import org.example.user.entity.User;
import org.example.user.models.UserModel;
import org.example.user.service.UserService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.util.Optional;

@FacesConverter(forClass = UserModel.class, managed = true)
public class UserConverter implements Converter<UserModel> {
    private final UserService service;

    @Inject
    public UserConverter(UserService service){
        this.service = service;
    }

    @Override
    public UserModel getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<User> user = service.find(Long.valueOf(value));
        return user.isEmpty() ? null : UserModel.entityToModelMapper().apply(user.get());

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, UserModel user) {
        return user == null ? "" : user.getName();
    }
}
