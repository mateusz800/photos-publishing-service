package org.example.camera.controller;

import lombok.NoArgsConstructor;
import org.example.camera.dto.AddCameraRequest;
import org.example.camera.dto.GetCameraResponse;
import org.example.camera.dto.GetCamerasResponse;
import org.example.camera.dto.UpdateCameraRequest;
import org.example.camera.entity.Brand;
import org.example.camera.entity.Camera;
import org.example.camera.model.CameraModel;
import org.example.camera.service.CameraService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.SortedMap;

@Path("/cameras")
@NoArgsConstructor
public class CameraController {
    @Inject
    private CameraService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCameras(){
        return Response.ok(GetCamerasResponse.entityToDtoMapper().apply(service.findAll()).getCameras()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCamera(@PathParam("id") Long id){
        Optional<Camera> camera = service.find(id);
        if(camera.isPresent()){
            return Response.ok(GetCameraResponse.entityToDtoMapper().apply(camera.get())).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCamera(AddCameraRequest request){
        Camera camera = AddCameraRequest.mapToEntity(request);
        service.create(camera);
        return Response.status(Response.Status.NO_CONTENT).build();
    }





    @DELETE
    @Path("{id}")
    public Response deleteCamera(@PathParam("id") Long id){
        Optional<Camera> camera = service.find(id);
        if(camera.isPresent()){
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
    public Response updateCamera(@PathParam("id") Long id, UpdateCameraRequest request){
        Optional<Camera> camera = service.find(id);
        if(camera.isPresent()){
            UpdateCameraRequest.dtoToEntityUpdater().apply(camera.get(), request);
            service.update(camera.get());
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
