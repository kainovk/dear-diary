package dev.kainov.deardiary.model.dto;

import dev.kainov.deardiary.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private LocalDate birthday;
    private String status;
    private Set<NoteDTO> notes;

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .birthday(user.getBirthday())
                .status(user.getStatus())
                .notes(user.getNotes().stream()
                        .map(NoteDTO::toDTO)
                        .collect(Collectors.toSet())
                )
                .build();
    }
}
