package org.example.photo.dto;

import lombok.*;
import org.example.photo.entity.Photo;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPhotoResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate creationDate;

    public static Function<Photo, GetPhotoResponse> entityToDtoMapper() {
        return photo-> GetPhotoResponse.builder()
                .id(photo.getId())
                .title(photo.getTitle())
                .description(photo.getDescription())
                .creationDate(photo.getCreationDate())
                .build();
    }
}
