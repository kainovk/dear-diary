package dev.kainov.deardiary.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private LocalDate birthday;
    private String status;
    private Set<NoteDTO> notes;
}
