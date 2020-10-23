package org.example.photo.models;

import lombok.*;
import org.example.camera.entity.Camera;
import org.example.photo.entity.Photo;
import org.example.user.entity.User;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PhotoModel {
    private Long id;
    private String title;
    private String description;
    private byte[] image;
    private User author;
    private Camera camera;

    public static Function<Photo, PhotoModel> entityToModelMapper() {
        return photo -> PhotoModel.builder()
                .id(photo.getId())
                .title(photo.getTitle())
                .description(photo.getDescription())
                .image(photo.getImage())
                .author(photo.getAuthor())
                .camera(photo.getCamera())
                .build();
    }

}
