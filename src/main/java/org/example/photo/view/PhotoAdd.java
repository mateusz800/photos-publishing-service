package org.example.photo.view;

import lombok.Getter;
import lombok.Setter;
import org.example.camera.model.CameraModel;
import org.example.camera.service.CameraService;
import org.example.photo.models.PhotoAddModel;
import org.example.photo.service.PhotoService;
import org.example.user.models.UserModel;
import org.example.user.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@ConversationScoped
@Named
public class PhotoAdd implements Serializable {
    private final PhotoService service;
    private final UserService userService;
    private final CameraService cameraService;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Part image;

    @Getter
    private PhotoAddModel photo;

    /*
    available users
     */
    @Getter
    private List<UserModel> users;

    /*
    available cameras
     */
    @Getter
    private List<CameraModel> cameras;


    private final Conversation conversation;

    @Inject
    public PhotoAdd(PhotoService photoService, UserService userService,CameraService cameraService, Conversation conversation) {
        this.service = photoService;
        this.userService = userService;
        this.cameraService = cameraService;
        this.conversation = conversation;
    }

    @PostConstruct
    public void init() {
        users = userService.findAll().stream()
                .map(UserModel.entityToModelMapper())
                .collect(Collectors.toList());
        cameras = cameraService.findAll().stream()
                .map(CameraModel.entityToModelMapper())
                .collect(Collectors.toList());
        photo = PhotoAddModel.builder().build();
        conversation.begin();
    }

    public String saveAction() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        photo.setTitle(request.getParameter("photoAddForm:title"));
        photo.setDescription(request.getParameter("photoAddForm:description"));
        photo.setAuthor(userService.find(Long.valueOf(request.getParameter("photoAddForm:author"))).get());
        photo.setCamera(cameraService.find(Long.valueOf(request.getParameter("photoAddForm:camera"))).get());
        //photo.setImage(request.getParameter("photoAddForm:image").getBytes());

        service.create(PhotoAddModel.modelToEntityMapper().apply(photo));
        //photo.setImage(image.getInputStream().readAllBytes());
        conversation.end();
        return "/photo/photo_list.xhtml?faces-redirect=true";
    }




}
