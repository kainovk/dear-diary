package dev.kainov.deardiary.service;

import dev.kainov.deardiary.dao.NoteRepo;
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
        return noteRepo.findById(id).orElseThrow(() -> new IllegalStateException("Note not found"));
    }

    public List<Note> findAll() {
        return noteRepo.findAll();
    }
}
