package com.ensolvers.NoteApp.controller.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for Note entities.
 * Used to transfer data between client and server without exposing the entity.
 */
@Getter
@Setter
@NoArgsConstructor
public class NoteDto {

    /**
     * Title of the note.
     */
    private String title;

    /**
     * Content of the note.
     */
    private String content;
}