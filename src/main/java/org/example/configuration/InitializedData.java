package org.example.configuration;

import lombok.SneakyThrows;
import org.example.camera.entity.Brand;
import org.example.camera.entity.Camera;
import org.example.photo.entity.Photo;
import org.example.photo.service.PhotoService;
import org.example.user.entity.Gender;
import org.example.user.entity.User;
import org.example.user.service.UserService;
import org.example.camera.service.CameraService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@ApplicationScoped
public class InitializedData {

    private final UserService userService;
    private final CameraService cameraService;
    private final PhotoService photoService;

    @Inject
    public InitializedData( UserService userService, CameraService cameraService, PhotoService photoService) {
        this.userService = userService;
        this.cameraService = cameraService;
        this.photoService = photoService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        User user1 = User.builder()
                .id((long) 1)
                .name("Adam")
                .birthDate(LocalDate.of(1996,12,5))
                .gender(Gender.MALE)
                //.avatar(getResourceAsByteArray("avatars/avatar4.png"))
                .build();
        User user2 = User.builder()
                .id((long) 2)
                .name("Mateusz")
                .gender(Gender.MALE)
                .birthDate((LocalDate.of(1986,4,28)))
                //.avatar(getResourceAsByteArray("avatars/avatar3.png"))
                .build();
        User user4 = User.builder()
                .id((long) 3)
                .name("Agata")
                .birthDate((LocalDate.of(1993,6,13)))
                .gender(Gender.FEMALE)
                //.avatar(getResourceAsByteArray("avatars/avatar2.png"))
                .build();
        User user3 = User.builder()
                .id((long) 4)
                .name("Ania")
                .birthDate((LocalDate.of(1999,10,2)))
                .gender(Gender.FEMALE)
                //.avatar(getResourceAsByteArray("avatars/avatar1.png"))
                .build();

        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.create(user4);

        Camera camera1 = Camera.builder()
                .id((long) 5)
                .brand(Brand.CANON)
                .model("EOS R6")
                .mpix(16f)
                .build();
        Camera camera2 = Camera.builder()
                .id((long) 6)
                .brand(Brand.NIKON)
                .model("test")
                .mpix(23f)
                .build();

        cameraService.create(camera1);
        cameraService.create(camera2);

        Photo photo1 = Photo.builder()
                .id((long)7)
                .title("Sample photo 1")
                .description("some description")
                .image(getResourceAsByteArray("photos/photo1.jpg"))
                .author(user1)
                .camera(camera1)
                .creationDate(LocalDate.now())
                .build();
        Photo photo2 = Photo.builder()
                .id((long)8)
                .title("Sample photo 2")
                .image(getResourceAsByteArray("photos/photo2.jpg"))
                .author(user1)
                .camera(camera1)
                .build();
        Photo photo3 = Photo.builder()
                .id((long)9)
                .title("Sample photo 3")
                .description("description")
                .image(getResourceAsByteArray("photos/photo3.jpg"))
                .author(user3)
                .camera(camera2)
                .creationDate(LocalDate.now())
                .build();

        photoService.create(photo1);
        photoService.create(photo2);
        photoService.create(photo3);

    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        URL resource = getClass().getClassLoader().getResource(name);
        Path path = Paths.get(resource.getPath());
        byte[] bytes = Files.readAllBytes(path);
        return bytes;

    }

}
