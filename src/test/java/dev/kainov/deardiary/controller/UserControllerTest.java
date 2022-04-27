package dev.kainov.deardiary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kainov.deardiary.exception.ApiRequestException;
import dev.kainov.deardiary.model.Note;
import dev.kainov.deardiary.model.User;
import dev.kainov.deardiary.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_Success() throws Exception {
        User user = prepareValidUser();

        when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void createUser_BlankNameReturn400() throws Exception {
        User user = prepareUserBlankName();

        when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_InvalidEmailReturn400() throws Exception {
        User user = prepareUserInvalidEmail();

        when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getById_Success() throws Exception {
        User user = prepareValidUser();
        long id = 1;
        user.setId(id);

        when(userService.findById(id)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(user)));
    }

    @Test
    void getById_IfUserNotFoundReturn400() throws Exception {
        long id = 1;

        when(userService.findById(id)).thenThrow(ApiRequestException.class);

        mockMvc.perform(get("/api/v1/users/{id}", id))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getAll_Empty() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getAll_NotEmpty() throws Exception {
        User user = prepareValidUser();
        user.setId(1L);

        when(userService.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().string("[" + objectMapper.writeValueAsString(user) + "]"));
    }

    @Test
    void updateById_Success() throws Exception {
        User user = prepareValidUser();
        long id = 1;
        user.setId(id);
        user.setStatus("other");

        when(userService.updateById(id, user)).thenReturn(user);

        mockMvc.perform(put("/api/v1/users/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void updateById_IfUserNotFoundReturn400() throws Exception {
        User user = prepareValidUser();
        long id = 1;
        user.setId(id);

        when(userService.updateById(anyLong(), any(User.class))).thenThrow(ApiRequestException.class);

        mockMvc.perform(put("/api/v1/users/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void updateById_IfUserBecomeNotValidReturn400() throws Exception {
        User user = prepareValidUser();
        long id = 1;
        user.setId(id);
        user.setEmail("123");

        when(userService.findById(anyLong())).thenReturn(prepareValidUser());
        when(userService.updateById(anyLong(), any(User.class))).thenThrow(ApiRequestException.class);

        mockMvc.perform(put("/api/v1/users/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("wrong email pattern")))
                .andDo(print());
    }

    @Test
    void deleteById_Success() throws Exception {
        User user = prepareValidUser();
        long id = 1;
        user.setId(id);

        when(userService.findById(anyLong())).thenReturn(user);

        mockMvc.perform(delete("/api/v1/users/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById_IfUserNotFoundReturn400() throws Exception {
        long id = 1;

        when(userService.deleteById(anyLong())).thenThrow(ApiRequestException.class);

        mockMvc.perform(delete("/api/v1/users/{id}", id))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void addNote_Success() throws Exception {
        User user = prepareValidUser();
        long id = 1;
        user.setId(id);
        Note note = prepareValidNote();

        when(userService.findById(anyLong())).thenReturn(user);

        mockMvc.perform(post("/api/v1/users/{user_id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk());
    }

    @Test
    void addNote_IfUserNotExistsReturn400() throws Exception {
        long id = 1;
        Note note = prepareValidNote();

        when(userService.addNote(anyLong(), any(Note.class))).thenThrow(ApiRequestException.class);

        mockMvc.perform(post("/api/v1/users/{user_id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void deleteNoteFromUser_IfNoteNotFoundReturn400() throws Exception {
        User user = prepareValidUser();
        long id = 1;
        user.setId(id);

        when(userService.deleteNoteById(id, id + 1)).thenThrow(ApiRequestException.class);

        mockMvc.perform(delete("/api/v1/users/{user_id}/{note_id}", id, id + 1))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    private User prepareValidUser() {
        return new User(
                "Kirill",
                "kirill@gmail.com",
                LocalDate.of(2001, 1, 8),
                "some"
        );
    }

    private User prepareUserBlankName() {
        return new User(
                "",
                "kirill@gmail.com",
                LocalDate.of(2001, 1, 8),
                "some"
        );
    }

    private User prepareUserInvalidEmail() {
        return new User(
                "Kirill",
                "@gmail.com",
                LocalDate.of(2001, 1, 8),
                "some"
        );
    }

    private Note prepareValidNote() {
        return new Note(
                "text",
                LocalDateTime.of(2022, 1, 1, 0, 0)
        );
    }
}