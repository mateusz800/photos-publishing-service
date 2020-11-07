package org.example.camera.model;

import lombok.*;
import org.example.camera.entity.Brand;
import org.example.camera.entity.Camera;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CameraModel {
    private Long id;
    private Brand brand;
    private String model;
    private float mpix;

    public static Function<Camera, CameraModel> entityToModelMapper() {
        return camera -> CameraModel.builder()
                .id(camera.getId())
                .brand(camera.getBrand())
                .model(camera.getModel())
                .mpix(camera.getMpix())
                .build();
    }
    public static CameraModel convertFromEntity(Camera entity){
        return CameraModel.builder()
                .id(entity.getId())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .mpix(entity.getMpix())
                .build();
    }
}
