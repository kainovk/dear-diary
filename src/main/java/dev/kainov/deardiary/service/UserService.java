package dev.kainov.deardiary.service;

import dev.kainov.deardiary.dao.UserRepo;
import dev.kainov.deardiary.exception.ApiRequestException;
import dev.kainov.deardiary.model.Note;
import dev.kainov.deardiary.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final NoteService noteService;

    public void save(User user) {
        userRepo.save(user);
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new ApiRequestException(String.format("User with id=%d not found", id))
        );
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void updateById(Long id, User user) {
        User userToUpdate = findById(id);
        userToUpdate.mapAttributes(
                user.getName(),
                user.getEmail(),
                user.getBirthday(),
                user.getStatus()
        );
        userRepo.save(userToUpdate);
    }

    public void deleteById(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
        } else {
            throw new ApiRequestException(String.format("User with id=%d not found", id));
        }
    }

    public void addNote(Long userId, Note note) {
        User user = findById(userId);
        note.setCreateTime(LocalDateTime.now());
        user.addNote(note);
        note.setUser(user);
        noteService.save(note);
    }

    public void deleteNoteById(Long userId, Long noteId) {
        Note note = noteService.findById(noteId);

        if (!Objects.equals(userId, note.getUser().getId())) {
            throw new ApiRequestException(
                    String.format("Note with id=%d does not belong to user with id=%d", noteId, userId)
            );
        }
        User user = findById(userId);
        user.removeNote(note);
        noteService.delete(note);
    }
}
