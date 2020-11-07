package org.example.photo.view;

import lombok.Getter;
import lombok.Setter;
import org.example.camera.model.CameraModel;
import org.example.camera.service.CameraService;
import org.example.photo.entity.Photo;
import org.example.photo.models.PhotoEditModel;
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
import java.util.stream.Collectors;

@ViewScoped
@Named
public class PhotoEdit  implements Serializable {
    private final PhotoService service;
    private final CameraService cameraService;

    @Getter
    @Setter
    private Long id;

    @Getter
    private PhotoEditModel photo;


    @Getter
    private List<CameraModel> cameras;

    @Inject
    public PhotoEdit(PhotoService service, CameraService cameraService) {
        this.service = service;
        this.cameraService = cameraService;
    }

    public void init() throws IOException {
        Optional<Photo> photo = service.find(id);
        if (photo.isPresent()) {
            this.photo = PhotoEditModel.entityToModelMapper().apply(photo.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Photo not found");
        }
        cameras = cameraService.findAll().stream()
                .map(CameraModel::convertFromEntity)
                .collect(Collectors.toList());

    }

    public String saveAction() {
        //HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //photo.setCamera(cameraService.find(Long.valueOf(request.getParameter("editPhotoForm:camera"))).get());
        service.update(PhotoEditModel.modelToEntityUpdater().apply(service.find(id).orElseThrow(), photo));
        //String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return "/camera/camera_view.xhtml?faces-redirect=true&includeViewParams=true&id="+photo.getCamera().getId();
    }


}
