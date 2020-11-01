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
public class AddCameraRequest {
    private Brand brand;
    private String model;
    private float mpix;

    public static Camera mapToEntity(AddCameraRequest request) {
        return Camera.builder()
                .brand(request.getBrand())
                .model(request.getModel())
                .mpix(request.getMpix())
                .build();
    }
}
