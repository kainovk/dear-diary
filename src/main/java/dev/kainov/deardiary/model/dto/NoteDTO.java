package dev.kainov.deardiary.model.dto;

import dev.kainov.deardiary.model.Note;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
public class NoteDTO {

    private Long id;
    private String text;
    private LocalDateTime createTime;
    private UserInsideNoteDTO user;

    public NoteDTO(Long id, String text, LocalDateTime createTime) {
        this.id = id;
        this.text = text;
        this.createTime = createTime;
    }

    public static NoteDTO toDTO(Note note) {
        NoteDTO noteDTO = new NoteDTO(
                note.getId(),
                note.getText(),
                note.getCreateTime()
        );

        if (Objects.nonNull(note.getUser())) {
            noteDTO.setUser(UserInsideNoteDTO.toDTO(note.getUser()));
        }
        return noteDTO;
    }
}
