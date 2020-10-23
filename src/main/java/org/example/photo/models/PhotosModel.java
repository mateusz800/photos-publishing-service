package org.example.photo.models;

import lombok.*;
import org.example.photo.entity.Photo;

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
public class PhotosModel {
    @Singular
    private List<Photo> photos;

    public static Function<Collection<Photo>, PhotosModel> entityToDtoMapper() {
        return photos -> {
            PhotosModel.PhotosModelBuilder response = PhotosModel.builder();
            photos.stream()
                    .map(photo -> Photo.builder()
                            .id(photo.getId())
                            .author(photo.getAuthor())
                            .camera(photo.getCamera())
                            .description(photo.getDescription())
                            .title(photo.getTitle())
                            .build())
                    .forEach(response::photo);
            return response.build();
        };
    }
}
