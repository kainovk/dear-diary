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

    public User save(User user) {
        Boolean existsByEmail = userRepo.existsByEmail(user.getEmail());
        if (existsByEmail) {
            throw new ApiRequestException("Email already taken");
        }
        return userRepo.save(user);
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new ApiRequestException(String.format("User with id=%d not found", id))
        );
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User updateById(Long id, User user) {
        User userToUpdate = findById(id);
        userToUpdate.mapAttributes(
                user.getName(),
                user.getEmail(),
                user.getBirthday(),
                user.getStatus()
        );
        return userRepo.save(userToUpdate);
    }

    public Long deleteById(Long id) {
        if (!userRepo.existsById(id)) {
            throw new ApiRequestException(String.format("User with id=%d not found", id));
        }
        userRepo.deleteById(id);
        return id;
    }

    public Note addNote(Long userId, Note note) {
        User user = findById(userId);
        note.setCreateTime(LocalDateTime.now());
        user.addNote(note);
        note.setUser(user);
        return noteService.save(note);
    }

    public Long deleteNoteById(Long userId, Long noteId) {
        Note note = noteService.findById(noteId);

        if (!Objects.equals(userId, note.getUser().getId())) {
            throw new ApiRequestException(
                    String.format("Note with id=%d does not belong to user with id=%d", noteId, userId)
            );
        }
        User user = findById(userId);
        user.removeNote(note);
        return noteService.delete(note);
    }

    public void deleteAll() {
        userRepo.deleteAll();
    }
}
