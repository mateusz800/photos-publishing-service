package org.example.photo.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.camera.entity.Camera;
import org.example.user.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name="photos")
public class Photo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate creationDate;


    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;


    @ManyToOne
    @JoinColumn(name ="author")
    private User author;

    @ManyToOne
    @JoinColumn(name ="camera")
    private Camera camera;
}
