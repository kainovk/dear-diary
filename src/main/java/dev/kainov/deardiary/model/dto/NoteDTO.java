package dev.kainov.deardiary.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NoteDTO {

    private Long id;
    private String text;
    private LocalDateTime createTime;
    private UserInsideNoteDTO userInsideNoteDTO;
}
