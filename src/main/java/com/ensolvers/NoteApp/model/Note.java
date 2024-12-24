package com.ensolvers.NoteApp.model;

import com.ensolvers.NoteApp.controller.note.NoteDto;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing a note in the application.
 */
@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    /**
     * Unique identifier for the note.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title of the note.
     */
    private String title;

    /**
     * Content of the note.
     */
    private String content;

    /**
     * Indicates whether the note is archived.
     * Defaults to false.
     */
    @Builder.Default
    private boolean archived = false;

    /**
     * Constructor to create a Note from a NoteDto object.
     * @param note The NoteDto object containing title and content.
     */
    public Note(NoteDto note) {
        this.title = note.getTitle();
        this.content = note.getContent();
    }
}