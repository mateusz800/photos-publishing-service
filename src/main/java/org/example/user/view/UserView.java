package org.example.user.view;

import lombok.Getter;
import lombok.Setter;
import org.example.user.entity.User;
import org.example.user.models.UserModel;
import org.example.user.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@RequestScoped
@Named
public class UserView implements Serializable {
    private UserService service;

    @Getter
    @Setter
    private Long id;

    @Getter
    private UserModel user;

    @Inject
    public UserView(UserService service){
        this.service = service;
    }

    public void init() throws IOException {
        Optional<User> user = service.find(id);
        if (user.isPresent()) {
            this.user = UserModel.entityToModelMapper().apply(user.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
        }
    }

}
