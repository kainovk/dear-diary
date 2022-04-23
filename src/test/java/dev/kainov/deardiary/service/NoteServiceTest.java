package dev.kainov.deardiary.service;

import dev.kainov.deardiary.dao.NoteRepo;
import dev.kainov.deardiary.exception.ApiRequestException;
import dev.kainov.deardiary.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepo noteRepo;
    @InjectMocks
    private NoteService underTest;

    @BeforeEach
    void setUp() {
        underTest = new NoteService(noteRepo);
    }

    @Test
    void save_ShouldTriggerRepository() {
        // given
        Note note = prepareValidNote();

        // when
        underTest.save(note);

        // then
        ArgumentCaptor<Note> captor = ArgumentCaptor.forClass(Note.class);
        verify(noteRepo).save(captor.capture());

        assertThat(captor.getValue()).isEqualTo(note);
    }

    @Test
    void findById_ShouldTriggerRepository() {
        // given
        long id = 1;
        Note note = prepareValidNote();
        note.setId(id);

        // when
        when(noteRepo.findById(id)).thenReturn(Optional.of(note));
        Note found = underTest.findById(id);

        // then
        verify(noteRepo).findById(id);
        assertThat(found).isEqualTo(note);
    }

    @Test
    void findById_ShouldThrowOnNonExistingId() {
        // given
        long id = 1;

        // when
        // then
        assertThatThrownBy(() -> underTest.findById(id))
                .isInstanceOf(ApiRequestException.class)
                .hasMessage("Note with id=%d not found", id);
    }

    @Test
    void findAll_ShouldTriggerRepository() {
        // when
        underTest.findAll();

        // then
        verify(noteRepo).findAll();
    }

    @Test
    void findAll_WhenFoundOneShouldReturnOne() {
        // given
        Note note = prepareValidNote();

        // when
        when(noteRepo.findAll()).thenReturn(List.of(note));
        List<Note> notes = underTest.findAll();

        // then
        assertThat(notes.size()).isOne();
    }

    @Test
    void delete_ShouldTriggerRepository() {
        // given
        Note note = prepareValidNoteWithId();
        noteRepo.save(note);

        // when
        when(noteRepo.existsById(anyLong())).thenReturn(true);
        underTest.delete(note);

        // then
        ArgumentCaptor<Note> captor = ArgumentCaptor.forClass(Note.class);
        verify(noteRepo).delete(captor.capture());
        assertThat(captor.getValue()).isEqualTo(note);
    }

    @Test
    void delete_ShouldThrowOnNonExistingNote() {
        // given
        Note note = prepareValidNote();
        long id = 1;
        note.setId(id);

        // when
        doReturn(false).when(noteRepo).existsById(anyLong());

        // then
        assertThatThrownBy(() -> underTest.delete(note))
                .isInstanceOf(ApiRequestException.class)
                .hasMessage("Note with id=%d not found", id);
    }

    private Note prepareValidNote() {
        return new Note(
                "text",
                LocalDateTime.of(2022, 1, 1, 0, 0)
        );
    }

    private Note prepareValidNoteWithId() {
        Note note = new Note(
                "text",
                LocalDateTime.of(2022, 1, 1, 0, 0)
        );
        note.setId(1L);
        return note;
    }
}