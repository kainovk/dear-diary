package dev.kainov.deardiary.controller;

import dev.kainov.deardiary.model.Note;
import dev.kainov.deardiary.service.NoteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NoteService noteService;

    @AfterEach
    void tearDown() {
        noteService.deleteAll();
    }

    @Test
    void findById_Success() throws Exception {
        Note note = prepareValidNote();
        noteService.save(note);
        note.setId(2L);

        mockMvc.perform(get("/api/v1/notes/{id}", note.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(note.getId().toString())))
                .andExpect(content().string(containsString(note.getText())))
                .andExpect(content().string(containsString(note.getCreateTime().toString())));
    }

    @Test
    void findById_IfUserNotFoundReturn400() throws Exception {
        mockMvc.perform(get("/api/v1/notes/{id}", 1L))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAll_Empty() throws Exception {
        mockMvc.perform(get("/api/v1/notes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[" + "]"));
    }

    @Test
    void findAll_NotEmpty() throws Exception {
        Note note = prepareValidNote();
        noteService.save(note);
        note.setId(1L);

        mockMvc.perform(get("/api/v1/notes/{id}", note.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(note.getId().toString())))
                .andExpect(content().string(containsString(note.getText())))
                .andExpect(content().string(containsString(note.getCreateTime().toString())));
    }

    private Note prepareValidNote() {
        return new Note(
                "text",
                LocalDateTime.of(2022, 1, 1, 0, 0)
        );
    }
}
