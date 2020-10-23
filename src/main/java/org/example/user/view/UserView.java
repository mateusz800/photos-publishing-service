package org.example.user.view;

import lombok.Getter;
import lombok.Setter;
import org.example.photo.entity.Photo;
import org.example.photo.models.PhotosModel;
import org.example.photo.service.PhotoService;
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
import java.util.List;
import java.util.Optional;

@RequestScoped
@Named
public class UserView implements Serializable {
    private UserService userService;
    private PhotoService photoService;

    @Getter
    @Setter
    private Long id;

    @Getter
    private UserModel user;

    @Getter
    private PhotosModel photos;

    @Inject
    public UserView(UserService userService, PhotoService photoService){
        this.userService = userService;
        this.photoService = photoService;
    }

    public void init() throws IOException {
        initUser();
        initUserPhotos();
    }

    private void initUser() throws IOException {
        Optional<User> user = userService.find(id);
        if (user.isPresent()) {
            this.user = UserModel.entityToModelMapper().apply(user.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
        }
    }

    private void initUserPhotos(){
        List<Photo> photos = photoService.findUserPhotos(id);
        this.photos = PhotosModel.entityToDtoMapper().apply(photos);
    }

}
