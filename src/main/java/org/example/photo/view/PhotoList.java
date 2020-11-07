package org.example.photo.view;

import org.example.photo.models.PhotosModel;
import org.example.photo.service.PhotoService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class PhotoList {
    private final PhotoService service;
    private PhotosModel photos;

    @Inject
    public PhotoList(PhotoService service){
        this.service = service;
    }

    public PhotosModel getPhotos(){
        if(photos == null){
            photos = PhotosModel.entityToDtoMapper().apply(service.findAll());
        }
        return photos;
    }
}
