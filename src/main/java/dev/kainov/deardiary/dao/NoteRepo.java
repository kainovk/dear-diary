package dev.kainov.deardiary.dao;

import dev.kainov.deardiary.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepo extends JpaRepository<Note, Long> {
}
