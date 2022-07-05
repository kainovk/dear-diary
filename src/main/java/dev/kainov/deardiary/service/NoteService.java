package dev.kainov.deardiary.service;

import dev.kainov.deardiary.dao.NoteRepo;
import dev.kainov.deardiary.exception.ApiRequestException;
import dev.kainov.deardiary.model.Note;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {

    private static final Logger log = LoggerFactory.getLogger(NoteService.class);

    private final NoteRepo noteRepo;

    public Note save(Note note) {
        return noteRepo.save(note);
    }

    public Note findById(Long id) {
        return noteRepo.findById(id).orElseThrow(() ->
                new ApiRequestException(String.format("Note with id=%d not found", id))
        );
    }

    public List<Note> findAll() {
        return noteRepo.findAll();
    }

    public Long delete(Note note) {
        long id = note.getId();
        if (!noteRepo.existsById(id)) {
            log.error("Error deleting note: note with id={} does not exist", id);
            throw new ApiRequestException(String.format("Note with id=%d does not exist", id));
        }
        noteRepo.delete(note);
        return id;
    }

    public void deleteAll() {
        noteRepo.deleteAll();
    }
}
