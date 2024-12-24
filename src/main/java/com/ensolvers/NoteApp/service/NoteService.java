package com.ensolvers.NoteApp.service;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ensolvers.NoteApp.model.Note;
import com.ensolvers.NoteApp.repository.NoteRepository;

/**
 * Service class that contains business logic for managing notes.
 */
@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    /**
     * Constructor for injecting NoteRepository.
     * @param noteRepository The NoteRepository instance.
     */
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Retrieves all notes from the database.
     * @return List of all notes.
     */
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    /**
     * Retrieves a specific note by its ID.
     * @param id ID of the note to retrieve.
     * @return Optional containing the note if found, or empty if not.
     */
    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    /**
     * Creates a new note in the database.
     * @param note The note to create.
     * @return The created note.
     */
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    /**
     * Retrieves all active (non-archived) notes.
     * @return List of active notes.
     */
    public List<Note> getActiveNotes() {
        return noteRepository.findByArchivedFalse();
    }

    /**
     * Retrieves all archived notes.
     * @return List of archived notes.
     */
    public List<Note> getArchivedNotes() {
        return noteRepository.findByArchivedTrue();
    }

    /**
     * Archives a note by its ID.
     * @param id ID of the note to archive.
     * @return The updated note with archived status set to true.
     * @throws IllegalArgumentException If the note is not found.
     */
    public Note archiveNote(Long id) {
        return noteRepository.findById(id).map(note -> {
            note.setArchived(true);
            return noteRepository.save(note);
        }).orElseThrow(() -> new IllegalArgumentException("Note not found with ID: " + id));
    }

    /**
     * Unarchives a note by its ID.
     * @param id ID of the note to unarchive.
     * @return The updated note with archived status set to false.
     * @throws IllegalArgumentException If the note is not found.
     */
    public Note unarchiveNote(Long id) {
        return noteRepository.findById(id).map(note -> {
            note.setArchived(false);
            return noteRepository.save(note);
        }).orElseThrow(() -> new IllegalArgumentException("Note not found with ID: " + id));
    }

    /**
     * Updates an existing note by its ID.
     * @param id ID of the note to update.
     * @param updatedNote The note data to update.
     * @return The updated note.
     * @throws IllegalArgumentException If the note is not found.
     */
    public Note updateNote(Long id, Note updatedNote) {
        return noteRepository.findById(id)
                .map(note -> {
                    note.setTitle(updatedNote.getTitle());
                    note.setContent(updatedNote.getContent());
                    return noteRepository.save(note);
                })
                .orElseThrow(() -> new IllegalArgumentException("Note not found with ID: " + id));
    }

    /**
     * Deletes a note by its ID.
     * @param id ID of the note to delete.
     * @throws IllegalArgumentException If the note is not found.
     */
    public void deleteNote(Long id) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Note not found with ID: " + id);
        }
    }
}