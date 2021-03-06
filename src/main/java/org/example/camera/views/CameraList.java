package org.example.camera.views;

import org.example.camera.model.CamerasModel;
import org.example.camera.service.CameraService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class CameraList implements Serializable {
    private final CameraService service;
    private CamerasModel cameras;

    @Inject
    public CameraList(CameraService service){
        this.service = service;
    }

    public CamerasModel getCameras(){
        if(cameras == null){
            cameras = CamerasModel.entityToDtoMapper().apply(service.findAll());
        }
        return cameras;
    }

    public String deleteAction(CamerasModel.Camera camera){
        service.delete(camera.getId());
        return "camera_list?faces-redirect=true";
    }
}
