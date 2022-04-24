package dev.kainov.deardiary.service;

import dev.kainov.deardiary.dao.NoteRepo;
import dev.kainov.deardiary.dao.UserRepo;
import dev.kainov.deardiary.exception.ApiRequestException;
import dev.kainov.deardiary.model.Note;
import dev.kainov.deardiary.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private NoteRepo noteRepo;
    @InjectMocks
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepo, new NoteService(noteRepo));
    }

    @Test
    void save_ShouldTriggerRepository() {
        // given
        User user = prepareValidUser();

        // when
        underTest.save(user);

        // then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(user);
    }

    @Test
    void save_ShouldThrowOnAlreadyExistingUserEmail() {
        // given
        User user = prepareValidUser();

        // when
        when(userRepo.existsByEmail(anyString())).thenReturn(true);

        // then
        assertThatThrownBy(() -> underTest.save(user))
                .isInstanceOf(ApiRequestException.class)
                .hasMessage("Email already taken");
    }

    @Test
    void findById_ShouldTriggerRepository() {
        // given
        User user = prepareValidUser();
        long id = 1;
        user.setId(id);

        // when
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        User found = underTest.findById(id);

        // then
        verify(userRepo).findById(id);
        assertThat(found).isEqualTo(user);
    }

    @Test
    void findById_ShouldThrowOnNonExistingId() {
        // given
        long id = 1;

        // when
        // then
        assertThatThrownBy(() -> underTest.findById(id))
                .isInstanceOf(ApiRequestException.class)
                .hasMessage("User with id=%d not found", id);
    }

    @Test
    void findAll() {
        // when
        underTest.findAll();

        // then
        verify(userRepo).findAll();
    }

    @Test
    void updateById_ShouldTriggerRepository() {
        // given
        long id = 1;
        User user = prepareValidUser();
        user.setId(id);

        // when
        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        underTest.updateById(id, user);

        // then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepo, times(1)).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(user);
    }

    @Test
    void deleteById_ShouldTriggerRepository() {
        // given
        User user = prepareValidUser();
        userRepo.save(user);

        long id = 1;
        user.setId(id);

        // when
        when(userRepo.existsById(id)).thenReturn(true);
        underTest.deleteById(id);

        // then
        verify(userRepo).deleteById(id);
    }

    @Test
    void deleteById_ShouldThrowOnNonExistingId() {
        // given
        long id = 1;

        // when
        // then
        assertThatThrownBy(() -> underTest.deleteById(id))
                .isInstanceOf(ApiRequestException.class)
                .hasMessage("User with id=%d not found", id);
    }

    @Test
    void addNote_Success() {
        // given
        long id = 1;

        User user = prepareValidUser();
        Note note = prepareValidNote();

        // when
        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        when(noteRepo.findById(id)).thenReturn(Optional.of(note));
        underTest.addNote(id, note);

        Optional<User> foundUser = userRepo.findById(id);
        Optional<Note> foundNote = noteRepo.findById(id);

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getNotes()).isEqualTo(Set.of(note));

        assertThat(foundNote).isPresent();
        assertThat(foundNote.get().getUser()).isEqualTo(user);
    }

    @Test
    void deleteNoteById_Success() {
        // given
        long id = 1;
        User user = prepareValidUser();
        user.setId(id);
        Note note = prepareValidNote();
        note.setId(id);

        user.addNote(note);
        note.setUser(user);

        // when
        when(noteRepo.findById(anyLong())).thenReturn(Optional.of(note));
        when(noteRepo.existsById(anyLong())).thenReturn(true);
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        underTest.deleteNoteById(id, id);

        // then
        verify(noteRepo).delete(note);
    }

    @Test
    void deleteNoteById_ShouldThrowOnInvalidInput() {
        // given
        long userId = 1;
        long noteId = 1;

        User user = prepareValidUser();
        user.setId(userId);
        Note note = prepareValidNote();
        note.setId(noteId);

        note.setUser(user);

        // when
        when(noteRepo.findById(noteId)).thenReturn(Optional.of(note));

        // then
        assertThatThrownBy(() -> underTest.deleteNoteById(userId + 1, noteId))
                .isInstanceOf(ApiRequestException.class)
                .hasMessage("Note with id=%d does not belong to user with id=%d", noteId, userId + 1);
    }

    private User prepareValidUser() {
        return new User(
                "Kirill",
                "kirill@gmail.com",
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