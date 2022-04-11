package dev.kainov.deardiary.controller;

import dev.kainov.deardiary.model.dto.NoteDTO;
import dev.kainov.deardiary.service.NoteService;
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
    public NoteDTO findById(@PathVariable Long id) {
        return NoteDTO.toDTO(noteService.findById(id));
    }

    @GetMapping
    public List<NoteDTO> findAll() {
        return noteService.findAll().stream()
                .map(NoteDTO::toDTO)
                .collect(Collectors.toList());
    }
}
