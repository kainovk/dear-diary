package dev.kainov.deardiary.controller;

import dev.kainov.deardiary.model.dto.NoteDTO;
import dev.kainov.deardiary.service.NoteService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/notes")
@AllArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение записи",
            description = "Получение записи по id"
    )
    public NoteDTO findById(@PathVariable Long id) {
        return NoteDTO.toDTO(noteService.findById(id));
    }

    @GetMapping
    @Operation(
            summary = "Получение всех записей",
            description = "Получение всех записей"
    )
    public List<NoteDTO> findAll() {
        return noteService.findAll().stream()
                .map(NoteDTO::toDTO)
                .collect(Collectors.toList());
    }
}
