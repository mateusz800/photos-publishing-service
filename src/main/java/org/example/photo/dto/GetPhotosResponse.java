package org.example.photo.dto;

import lombok.*;
import org.example.camera.entity.Brand;
import org.example.camera.entity.Camera;
import org.example.photo.entity.Photo;
import org.example.user.entity.User;

import java.time.LocalDate;
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
public class GetPhotosResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Photo {
        private Long id;
        private String title;
        private String description;
        private LocalDate creationDate;
        private Long cameraId;
        private Long authorId;

    }

    @Singular
    private List<Photo> photos;

    public static Function<Collection<org.example.photo.entity.Photo>, GetPhotosResponse> entityToDtoMapper() {
        return photos -> {
            GetPhotosResponseBuilder response = GetPhotosResponse.builder();
            photos.stream()
                    .map(photo-> Photo.builder()
                            .id(photo.getId())
                            .title(photo.getTitle())
                            .description(photo.getDescription())
                            .creationDate(photo.getCreationDate())
                            .cameraId(photo.getCamera().getId())
                            .authorId(photo.getAuthor().getId())
                            .build())
                    .forEach(response::photo);
            return response.build();
        };
    }
}