package dev.kainov.deardiary.model.dto;

import dev.kainov.deardiary.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserInsideNoteDTO {

    private Long id;
    private String name;
    private String email;
    private LocalDate birthday;
    private String status;

    public static UserInsideNoteDTO toDTO(User user) {
        return UserInsideNoteDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .status(user.getStatus())
                .build();
    }
}
