package dev.kainov.deardiary.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserInsideNoteDTO {

    private Long id;
    private String name;
    private String email;
    private LocalDate birthday;
    private String status;
}
