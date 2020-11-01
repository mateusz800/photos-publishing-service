package org.example.photo.dto;

import lombok.*;
import org.example.camera.entity.Brand;
import org.example.camera.entity.Camera;
import org.example.photo.entity.Photo;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdatePhotoRequest {
    private Long id;
    private String title;
    private String description;

    public static BiFunction<Photo, UpdatePhotoRequest, Photo> dtoToEntityUpdater() {
        return (camera, request) -> {
            if(request.getTitle() != null)
                camera.setTitle(request.getTitle());
            if(request.getDescription() != null)
                camera.setDescription(request.getDescription());
            return camera;
        };
    }
}
