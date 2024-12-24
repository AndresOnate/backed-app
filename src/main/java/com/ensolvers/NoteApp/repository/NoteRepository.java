package com.ensolvers.NoteApp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ensolvers.NoteApp.model.Note;

/**
 * Repository interface for managing Note entities in the database.
 * Extends JpaRepository to provide standard CRUD operations.
 */
public interface NoteRepository extends JpaRepository<Note, Long> {

    /**
     * Finds all active (non-archived) notes.
     * @return List of active notes.
     */
    List<Note> findByArchivedFalse();

    /**
     * Finds all archived notes.
     * @return List of archived notes.
     */
    List<Note> findByArchivedTrue();
}