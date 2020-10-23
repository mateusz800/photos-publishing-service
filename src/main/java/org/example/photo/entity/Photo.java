package org.example.photo.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.camera.entity.Camera;
import org.example.user.entity.User;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Photo implements Serializable {
    private Long id;
    private String title;
    private String description;
    private LocalDate creationDate;
    private byte[] image;
    private User author;
    private Camera camera;
}
