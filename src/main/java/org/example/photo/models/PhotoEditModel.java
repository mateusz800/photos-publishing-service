package org.example.photo.models;

import lombok.*;
import org.example.camera.entity.Camera;
import org.example.photo.entity.Photo;

import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PhotoEditModel {
    private String title;
    private String description;
    private Camera camera;

    public static Function<Photo, PhotoEditModel> entityToModelMapper() {
        return photo -> PhotoEditModel.builder()
                .title(photo.getTitle())
                .description(photo.getDescription())
                .camera(photo.getCamera())
                .build();
    }
    public static BiFunction<Photo, PhotoEditModel, Photo> modelToEntityUpdater() {
        return (photo, request) -> {
            photo.setTitle(request.getTitle());
            photo.setDescription(request.getDescription());
            photo.setCamera(request.getCamera());
            return photo;
        };
    }
}
