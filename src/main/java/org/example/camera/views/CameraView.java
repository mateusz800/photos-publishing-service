package org.example.camera.views;

import lombok.Getter;
import lombok.Setter;
import org.example.camera.entity.Camera;
import org.example.camera.model.CameraModel;
import org.example.camera.service.CameraService;
import org.example.photo.entity.Photo;
import org.example.photo.models.PhotosModel;
import org.example.photo.service.PhotoService;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@ViewScoped
@Named
public class CameraView implements Serializable {
    private CameraService service;
    private PhotoService photoService;

    @Getter
    @Setter
    private Long id;

    @Getter
    private CameraModel camera;

    @Getter
    private PhotosModel photos;

    @Inject
    public CameraView(CameraService service, PhotoService photoService){
        this.service = service;
        this.photoService = photoService;
    }

    public void init() throws IOException {
        Optional<Camera> camera = service.find(id);
        if (camera.isPresent()) {
            this.camera = CameraModel.entityToModelMapper().apply(camera.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Camera not found");
        }
        List<Photo> photos = photoService.findCameraPhotos(id);
        this.photos = PhotosModel.entityToDtoMapper().apply(photos);
    }

    public String deleteCameraAction(){
        service.delete(id);
        return "/camera/camera_list.xhtml";
    }

    public String deletePhotoAction(Long id){
        photoService.delete(id);
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
