package org.example.configuration;

import lombok.SneakyThrows;
import org.example.User.entity.User;
import org.example.User.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.InputStream;
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
                .build();
        User user2 = User.builder()
                .id((long) 2)
                .name("Mateusz")
                .build();
        User user4 = User.builder()
                .id((long) 3)
                .name("Agata")
                .build();
        User user3 = User.builder()
                .id((long) 4)
                .name("Ania")
                .build();
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.create(user4);

    }

    /**
     * @param name name of the desired resource
     * @return array of bytes read from the resource
     */
    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }

}
