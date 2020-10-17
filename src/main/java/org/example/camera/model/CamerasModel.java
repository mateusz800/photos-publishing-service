package org.example.camera.model;

import lombok.*;
import org.example.camera.entity.Camera;

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
public class CamerasModel {
    @Singular
    private List<Camera> cameras;

    public static Function<Collection<Camera>, CamerasModel> entityToDtoMapper() {
        return cameras -> {
            CamerasModel.CamerasModelBuilder response = CamerasModel.builder();
            cameras.stream()
                    .map(camera -> Camera.builder()
                            .id(camera.getId())
                            .brand(camera.getBrand())
                            .model(camera.getModel())
                            .build())
                    .forEach(response::camera);
            return response.build();
        };
    }
}
