package org.example.user.dto;

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
public class GetUserResponse {
    private String name;
    private LocalDate birthDate;
    private Gender gender;


    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .build();
    }

}
