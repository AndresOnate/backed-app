package com.ensolvers.NoteApp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ensolvers.NoteApp.model.Note;
import com.ensolvers.NoteApp.repository.NoteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNotes() {
        // Arrange
        Note note1 = new Note(1L, "Title 1 test", "Content 1", false);
        Note note2 = new Note(2L, "Title 2 test", "Content 2", false);
        when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));
        // Act
        List<Note> notes = noteService.getAllNotes();
        // Assert
        assertEquals(2, notes.size());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void testGetNoteById() {
        // Arrange
        Note note = new Note(1L, "Title 1 test", "Content 1", false);
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        // Act
        Optional<Note> retrievedNote = noteService.getNoteById(1L);
        // Assert
        assertTrue(retrievedNote.isPresent());
        assertEquals("Title 1 test", retrievedNote.get().getTitle());
        verify(noteRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateNote() {
        // Arrange
        Note note = new Note(null, "Title", "Content", false);
        Note savedNote = new Note(1L, "Title", "Content", false);
        when(noteRepository.save(note)).thenReturn(savedNote);
        // Act
        Note result = noteService.createNote(note);
        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void testArchiveNote() {
        // Arrange
        Note note = new Note(1L, "Title", "Content", false);
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(noteRepository.save(any(Note.class))).thenReturn(note);
        // Act
        Note archivedNote = noteService.archiveNote(1L);
        // Assert
        assertTrue(archivedNote.isArchived());
        verify(noteRepository, times(1)).findById(1L);
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void testDeleteNote() {
        // Arrange
        when(noteRepository.existsById(1L)).thenReturn(true);
        // Act
        noteService.deleteNote(1L);
        // Assert
        verify(noteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNote_NotFound() {
        // Arrange
        when(noteRepository.existsById(1L)).thenReturn(false);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> noteService.deleteNote(1L));
    }

     @Test
    void testUpdateNote() {
        Long noteId = 1L;
        Note existingNote = Note.builder()
                .id(noteId)
                .title("Old Title")
                .content("Old Content")
                .archived(false)
                .build();

        Note updatedNote = Note.builder()
                .title("New Title")
                .content("New Content")
                .archived(false)
                .build();

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // Act
        Note result = noteService.updateNote(noteId, updatedNote);
        // Assert
        assertNotNull(result);
        assertEquals(noteId, result.getId());
        assertEquals("New Title", result.getTitle());
        assertEquals("New Content", result.getContent());
        assertFalse(result.isArchived());
        verify(noteRepository).findById(noteId);
        verify(noteRepository).save(existingNote);
    }
}
