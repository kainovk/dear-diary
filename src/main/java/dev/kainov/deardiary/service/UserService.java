package dev.kainov.deardiary.service;

import dev.kainov.deardiary.dao.UserRepo;
import dev.kainov.deardiary.model.Note;
import dev.kainov.deardiary.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final NoteService noteService;

    public void save(User user) {
        userRepo.save(user);
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void updateById(Long id, User user) {
        User userToUpdate = findById(id);
        userToUpdate.setName(user.getName());
        userRepo.save(userToUpdate);
    }

    public void deleteById(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
        } else {
            throw new IllegalStateException("User does not exist");
        }
    }

    @Transactional
    public void addNote(Long userId, Note note) {
        User user = findById(userId);
        noteService.save(note);
        user.addNote(note);
        note.setUser(user);
    }
}
