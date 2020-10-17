package org.example.user.models;


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
public class UsersModel {
    @Singular
    private List<User> users;

    public static Function<Collection<User>, UsersModel> entityToDtoMapper() {
        return users -> {
            UsersModel.UsersModelBuilder response = UsersModel.builder();
            users.stream()
                    .map(user -> User.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .birthDate(user.getBirthDate())
                            .gender(user.getGender())
                            .avatar(user.getAvatar())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }
}
