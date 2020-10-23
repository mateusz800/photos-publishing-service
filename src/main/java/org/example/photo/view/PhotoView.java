package org.example.photo.view;

import lombok.Getter;
import lombok.Setter;
import org.example.photo.entity.Photo;
import org.example.photo.models.PhotoModel;
import org.example.photo.service.PhotoService;

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
public class PhotoView implements Serializable {
    private PhotoService service;

    @Getter
    @Setter
    private Long id;

    @Getter
    private PhotoModel photo;

    @Inject
    public PhotoView(PhotoService service){
        this.service = service;
    }

    public void init() throws IOException {
        Optional<Photo> photo = service.find(id);
        if (photo.isPresent()) {
            this.photo = PhotoModel.entityToModelMapper().apply(photo.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Photo not found");
        }
    }

}
