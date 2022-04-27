package dev.kainov.deardiary.controller;

import dev.kainov.deardiary.model.Note;
import dev.kainov.deardiary.model.User;
import dev.kainov.deardiary.model.request.NoteRequest;
import dev.kainov.deardiary.model.dto.UserDTO;
import dev.kainov.deardiary.model.request.UserRequest;
import dev.kainov.deardiary.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "diary-service",
                version = "1.0.0",
                description = "Контроллер предоставляет операции над пользователями и их записями"
        )
)
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(
            summary = "Создание пользователя",
            description = "Создание нового пользователя. Выполняется валидация UserRequest")
    public void create(@RequestBody @Valid UserRequest userRequest) {
        userService.save(User.toUser(userRequest));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение пользователя",
            description = "Получение пользователя по id"
    )
    public UserDTO getById(@PathVariable Long id) {
        return UserDTO.toDTO(userService.findById(id));
    }

    @GetMapping
    @Operation(
            summary = "Получение всех пользователей",
            description = "Получение всех пользователей"
    )
    public List<UserDTO> getAll() {
        return userService.findAll().stream()
                .map(UserDTO::toDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление пользователя",
            description = "Обновление существующего пользователя по id и UserRequest с новыми данными. " +
                    "Выполняется валидация UserRequest"
    )
    public void updateById(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest) {
        userService.updateById(id, User.toUser(userRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление пользователя",
            description = "Удаление существующего пользователя по id"
    )
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PostMapping("/{user_id}")
    @Operation(
            summary = "Добавление записи",
            description = "Добавление новой записи NoteRequest к существующему пользователю по его id. " +
                    "Выполняется валидация NoteRequest"
    )
    public void addNote(@PathVariable("user_id") Long userId, @RequestBody @Valid NoteRequest noteRequest) {
        userService.addNote(userId, Note.toNote(noteRequest));
    }

    @DeleteMapping("/{user_id}/{note_id}")
    @Operation(
            summary = "Удаление записи",
            description = "Удаление существующей записи у пользователя по id пользователя и id записи"
    )
    public void deleteNote(@PathVariable("user_id") Long userId, @PathVariable("note_id") Long noteId) {
        userService.deleteNoteById(userId, noteId);
    }
}
