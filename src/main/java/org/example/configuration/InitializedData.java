package org.example.configuration;

import lombok.SneakyThrows;
import org.example.user.entity.Gender;
import org.example.user.entity.User;
import org.example.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

/**
 * Listener started automatically on CDI application context initialized. Injects proxy to the services and fills
 * database with default content. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@ApplicationScoped
public class InitializedData {

    /**
     * Service for characters operations.
     */


    /**
     * Service for users operations.
     */
    private final UserService userService;


    @Inject
    public InitializedData( UserService userService) {
        this.userService = userService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should
     * be created only once.
     */
    private synchronized void init() {
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
                .avatar(getResourceAsByteArray("avatars/avatar1.png"))
                .build();

        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.create(user4);

    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        URL resource = getClass().getClassLoader().getResource(name);
        Path path = Paths.get(resource.getPath());
        byte[] bytes = Files.readAllBytes(path);
        return bytes;

    }

}
