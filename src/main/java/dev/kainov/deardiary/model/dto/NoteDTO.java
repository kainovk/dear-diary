package dev.kainov.deardiary.model.dto;

import dev.kainov.deardiary.model.Note;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class NoteDTO {

    private Long id;
    private String text;
    private UserInsideNoteDTO user;

    public static NoteDTO toDTO(Note note) {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setId(note.getId());
        noteDTO.setText(note.getText());
        if (Objects.nonNull(note.getUser())) {
            noteDTO.setUser(UserInsideNoteDTO.toDTO(note.getUser()));
        }
        return noteDTO;
    }
}
