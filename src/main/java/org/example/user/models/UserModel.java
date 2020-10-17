package org.example.user.models;

import lombok.*;
import org.example.user.entity.Gender;
import org.example.user.entity.User;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UserModel {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private byte[] avatar;


    public static Function<User, UserModel> entityToModelMapper() {
        return user -> UserModel.builder()
                .id(user.getId())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .avatar(user.getAvatar())
                .build();
    }

}
