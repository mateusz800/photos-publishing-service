package org.example.camera.dto;

import lombok.*;
import org.example.camera.entity.Brand;
import org.example.camera.entity.Camera;

import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateCameraRequest {
    private Long id;
    private Brand brand;
    private String model;
    private float mpix;

    public static BiFunction<Camera, UpdateCameraRequest, Camera> dtoToEntityUpdater() {
        return (camera, request) -> {
            if(request.getBrand() != null)
                camera.setBrand(request.getBrand());
            if(request.getModel() != null)
                camera.setModel(request.getModel());
            camera.setMpix(request.getMpix());

            return camera;
        };
    }
}
