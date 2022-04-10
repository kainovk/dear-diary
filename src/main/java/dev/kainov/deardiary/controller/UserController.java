package dev.kainov.deardiary.controller;


import dev.kainov.deardiary.model.Note;
import dev.kainov.deardiary.model.User;
import dev.kainov.deardiary.model.dto.NoteDTO;
import dev.kainov.deardiary.model.dto.UserDTO;
import dev.kainov.deardiary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody UserDTO user) {
        userService.save(User.toUser(user));
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return UserDTO.toDTO(userService.findById(id));
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.findAll().stream()
                .map(UserDTO::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/{user_id}")
    public void addNote(@PathVariable("user_id") Long userId, @RequestBody NoteDTO note) {
        userService.addNote(userId, Note.toNote(note));
    }
}
