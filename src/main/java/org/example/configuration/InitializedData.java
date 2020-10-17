package org.example.configuration;

import lombok.SneakyThrows;
import org.example.camera.entity.Brand;
import org.example.camera.entity.Camera;
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


@ApplicationScoped
public class InitializedData {

    private final UserService userService;
    private final CameraService cameraService;

    @Inject
    public InitializedData( UserService userService, CameraService cameraService) {
        this.userService = userService;
        this.cameraService = cameraService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        initCameras();
        initUsers();

    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        URL resource = getClass().getClassLoader().getResource(name);
        Path path = Paths.get(resource.getPath());
        byte[] bytes = Files.readAllBytes(path);
        return bytes;

    }

    private void initUsers(){
        User user1 = User.builder()
                .id((long) 1)
                .name("Adam")
                .birthDate(LocalDate.of(1996,12,5))
                .gender(Gender.MALE)
                .avatar(getResourceAsByteArray("avatars/avatar4.png"))
                .build();
        User user2 = User.builder()
                .id((long) 2)
                .name("Mateusz")
                .gender(Gender.MALE)
                .birthDate((LocalDate.of(1986,4,28)))
                .avatar(getResourceAsByteArray("avatars/avatar3.png"))
                .build();
        User user4 = User.builder()
                .id((long) 3)
                .name("Agata")
                .birthDate((LocalDate.of(1993,6,13)))
                .gender(Gender.FEMALE)
                .avatar(getResourceAsByteArray("avatars/avatar2.png"))
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
    }

    private void initCameras(){
        Camera camera1 = Camera.builder()
                .id((long) 1)
                .brand(Brand.CANON)
                .model("EOS R6")
                .build();
        Camera camera2 = Camera.builder()
                .id((long) 2)
                .brand(Brand.NIKON)
                .model("test")
                .build();

        cameraService.create(camera1);
        cameraService.create(camera2);
    }

}
