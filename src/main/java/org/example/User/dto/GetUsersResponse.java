package org.example.User.dto;


import lombok.*;
import org.example.User.entity.User;

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
public class GetUsersResponse {

    /**
     * Represents single character in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Userr {
        private Long id;
        private String name;
    }

    @Singular
    private List<User> users;

    public static Function<Collection<User>, GetUsersResponse> entityToDtoMapper() {
        return characters -> {
            GetUsersResponse.GetUsersResponseBuilder response = GetUsersResponse.builder();
            characters.stream()
                    .map(user -> User.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }

}
