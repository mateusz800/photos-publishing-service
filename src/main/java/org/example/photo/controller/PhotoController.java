package org.example.photo.controller;

import lombok.NoArgsConstructor;
import org.example.camera.entity.Camera;
import org.example.camera.service.CameraService;
import org.example.photo.dto.AddPhotoRequest;
import org.example.photo.dto.GetPhotoResponse;
import org.example.photo.dto.GetPhotosResponse;
import org.example.photo.dto.UpdatePhotoRequest;
import org.example.photo.entity.Photo;
import org.example.photo.service.PhotoService;
import org.example.user.entity.User;
import org.example.user.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("cameras/{cameraId}/photos")
@NoArgsConstructor
public class PhotoController {
    @Inject
    private PhotoService service;

    @Inject
    private CameraService cameraService;

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhotos(@PathParam("cameraId") Long cameraId){
        return Response.ok(GetPhotosResponse.entityToDtoMapper().apply(service.findCameraPhotos(cameraId)).getPhotos()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhoto(@PathParam("id") Long id){
        Optional<Photo> photo = service.find(id);
        if(photo.isPresent()){
            return Response.ok(GetPhotoResponse.entityToDtoMapper().apply(photo.get())).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPhoto(AddPhotoRequest request, @PathParam("cameraId") Long cameraId){
        Photo photo = AddPhotoRequest.mapToEntity(request);
        if(request.getCameraId() != null){
            Optional<Camera> camera = cameraService.find(cameraId);
            if(camera.isPresent()){
                photo.setCamera(camera.get());
            }
            else{
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        if(request.getAuthorId() != null){
            Optional<User> author = userService.find(request.getAuthorId());
            if(author.isPresent()){
                photo.setAuthor(author.get());
            }
            else{
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }


        service.create(photo);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePhoto(@PathParam("id") Long id){
        Optional<Photo> photo = service.find(id);
        if(photo.isPresent()){
            service.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePhoto(@PathParam("id") Long id, UpdatePhotoRequest request){
        Optional<Photo> photo = service.find(id);
        if(photo.isPresent()){
            UpdatePhotoRequest.dtoToEntityUpdater().apply(photo.get(), request);
            service.update(photo.get());
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
