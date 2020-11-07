package org.example.camera.dto;

import lombok.*;
import org.example.camera.entity.Brand;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetCamerasResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Camera {
        private Long id;
        private Brand brand;
        private String model;
        private float mpix;

    }

    @Singular
    private List<Camera> cameras;


    public static Function<Collection<org.example.camera.entity.Camera>, GetCamerasResponse> entityToDtoMapper() {
        return cameras -> {
            GetCamerasResponseBuilder response = GetCamerasResponse.builder();
            cameras.stream()
                    .map(camera -> Camera.builder()
                            .id(camera.getId())
                            .brand(camera.getBrand())
                            .model(camera.getModel())
                            .mpix(camera.getMpix())
                            .build())
                    .forEach(response::camera);
            return response.build();
        };
    }
}