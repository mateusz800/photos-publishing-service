package org.example.camera.dto;

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
public class GetCameraResponse {
    private Long id;
    private Brand brand;
    private String model;
    private float mpix;

    public static Function<Camera, GetCameraResponse> entityToDtoMapper() {
        return camera -> GetCameraResponse.builder()
                .id(camera.getId())
                .brand(camera.getBrand())
                .model(camera.getModel())
                .mpix(camera.getMpix())
                .build();
    }
}
