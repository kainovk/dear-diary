package dev.kainov.deardiary.service;

import dev.kainov.deardiary.dao.NoteRepo;
import dev.kainov.deardiary.exception.ApiRequestException;
import dev.kainov.deardiary.model.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {

    private final NoteRepo noteRepo;

    public void save(Note note) {
        noteRepo.save(note);
    }

    public Note findById(Long id) {
        return noteRepo.findById(id).orElseThrow(() ->
                new ApiRequestException(String.format("Note with id=%d not found", id))
        );
    }

    public List<Note> findAll() {
        return noteRepo.findAll();
    }

    public void delete(Note note) {
        if (noteRepo.existsById(note.getId())) {
            noteRepo.delete(note);
        } else {
            throw new ApiRequestException(String.format("Note with id=%d not found", note.getId()));
        }
    }

    public void deleteAll() {
        noteRepo.deleteAll();
    }
}
