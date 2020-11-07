package org.example.photo.dto;

import lombok.*;
import org.example.camera.service.CameraService;
import org.example.photo.entity.Photo;

import javax.inject.Inject;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class AddPhotoRequest {
    private String title;
    private String description;
    private LocalDate creationDate;
    private Long cameraId;
    private Long authorId;

    @Inject
    private CameraService cameraService;

    public static Photo mapToEntity(AddPhotoRequest request) {
        return Photo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creationDate(request.getCreationDate())
                .build();
    }
}
