package com.ensolvers.NoteApp.controller.note;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ensolvers.NoteApp.model.Note;
import com.ensolvers.NoteApp.service.NoteService;

import java.util.List;

/**
 * REST controller for managing notes.
 */
@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Creates a new note.
     * @param note The NoteDto object containing title and content.
     * @return The created Note object.
     */
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody NoteDto note) {
        Note createdNote = noteService.createNote(new Note(note));
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    /**
     * Retrieves all notes.
     * @return A list of all notes.
     */
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    /**
     * Retrieves all active notes.
     * @return A list of active notes.
     */
    @GetMapping("/active")
    public ResponseEntity<List<Note>> getActiveNotes() {
        List<Note> activeNotes = noteService.getActiveNotes();
        return new ResponseEntity<>(activeNotes, HttpStatus.OK);
    }

    /**
     * Retrieves all archived notes.
     * @return A list of archived notes.
     */
    @GetMapping("/archived")
    public ResponseEntity<List<Note>> getArchivedNotes() {
        List<Note> archivedNotes = noteService.getArchivedNotes();
        return new ResponseEntity<>(archivedNotes, HttpStatus.OK);
    }

    /**
     * Retrieves a note by its ID.
     * @param id The ID of the note.
     * @return The note if found, or 404 if not.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id)
                .map(note -> new ResponseEntity<>(note, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates a note by its ID.
     * @param id The ID of the note.
     * @param updatedNote The updated NoteDto object.
     * @return The updated Note object, or 404 if not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody NoteDto updatedNote) {
        try {
            Note note = noteService.updateNote(id, new Note(updatedNote));
            return new ResponseEntity<>(note, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Archives a note by its ID.
     * @param id The ID of the note.
     * @return The archived note.
     */
    @PutMapping("/{id}/archive")
    public ResponseEntity<Note> archiveNote(@PathVariable Long id) {
        try {
            Note archivedNote = noteService.archiveNote(id);
            return new ResponseEntity<>(archivedNote, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Unarchives a note by its ID.
     * @param id The ID of the note.
     * @return The unarchived note.
     */
    @PutMapping("/{id}/unarchive")
    public ResponseEntity<Note> unarchiveNote(@PathVariable Long id) {
        try {
            Note unarchivedNote = noteService.unarchiveNote(id);
            return new ResponseEntity<>(unarchivedNote, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a note by its ID.
     * @param id The ID of the note.
     * @return No content if successful, or 404 if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        try {
            noteService.deleteNote(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
