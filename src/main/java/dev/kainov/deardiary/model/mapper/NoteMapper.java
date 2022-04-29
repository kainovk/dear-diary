package dev.kainov.deardiary.model.mapper;

import dev.kainov.deardiary.model.Note;
import dev.kainov.deardiary.model.dto.NoteDTO;
import dev.kainov.deardiary.model.request.NoteRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoteMapper {

    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    Note noteRequestToNote(NoteRequest noteRequest);
    @Mapping(
            target = "userInsideNoteDTO",
            expression = "java(UserMapper.INSTANCE.userInsideUserDTOToUser(note.getUser()))"
    )
    NoteDTO noteToNoteDTO(Note note);
}
