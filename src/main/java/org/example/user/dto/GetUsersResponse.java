package org.example.user.dto;


import lombok.*;
import org.example.user.entity.User;

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
    public static class GetUsersRespoonse {
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
                            .birthDate(user.getBirthDate())
                            .gender(user.getGender())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }

}
