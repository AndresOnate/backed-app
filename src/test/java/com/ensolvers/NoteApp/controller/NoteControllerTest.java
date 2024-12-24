package com.ensolvers.NoteApp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ensolvers.NoteApp.controller.note.NoteController;
import com.ensolvers.NoteApp.model.Note;
import com.ensolvers.NoteApp.service.NoteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNotes() throws Exception {
        // Arrange
        Note note1 = new Note(1L, "Title 1", "Content 1", false);
        Note note2 = new Note(2L, "Title 2", "Content 2", false);
        when(noteService.getAllNotes()).thenReturn(Arrays.asList(note1, note2));
        // Act & Assert
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testGetNoteById() throws Exception {
        // Arrange
        Note note = new Note(1L, "Title 1", "Content 1", false);
        when(noteService.getNoteById(1L)).thenReturn(Optional.of(note));

        // Act & Assert
        mockMvc.perform(get("/api/notes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title 1"));
    }

    @Test
    void testCreateNote() throws Exception {
        // Arrange
        Note note = new Note(null, "Title", "Content", false);
        Note createdNote = new Note(1L, "Title", "Content", false);
        when(noteService.createNote(any(Note.class))).thenReturn(createdNote);
        // Act & Assert
        mockMvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Title\",\"content\":\"Content\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }
}
