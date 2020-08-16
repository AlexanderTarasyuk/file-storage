package com.example.clm.file.exceptions;

import java.util.ArrayList;

/**
 * The type No such tags.
 */
//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such tags")
public class NoSuchTags extends RuntimeException {
    /**
     * Instantiates a new No such tags.
     *
     * @param tags the tags
     */
    public NoSuchTags(ArrayList<String> tags) {
        super("No match tags" + tags.toString());
    }
}
