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
public class PhotoAddModel {

    private Long id;
    private String title;
    private String description;
    private User author;
    private Camera camera;
    private byte[] image;

    //@ToString.Exclude
    //@EqualsAndHashCode.Exclude
    //private byte[] image;


    public static Function<PhotoAddModel, Photo> modelToEntityMapper() {
        return model -> Photo.builder()
                .title(model.getTitle())
                .description(model.getDescription())
                .author(model.getAuthor())
                .camera(model.getCamera())
                .image(model.getImage())
                .build();
    }
}



