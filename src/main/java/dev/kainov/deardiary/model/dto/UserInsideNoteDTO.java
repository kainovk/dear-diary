package dev.kainov.deardiary.model.dto;

import dev.kainov.deardiary.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInsideNoteDTO {

    private Long id;
    private String name;

    public static UserInsideNoteDTO toDTO(User user) {
        return UserInsideNoteDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
