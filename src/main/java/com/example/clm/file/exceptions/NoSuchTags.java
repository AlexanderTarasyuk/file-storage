package com.example.clm.file.exceptions;

import java.util.List;

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
    public NoSuchTags(List<String> tags) {
        super("No match tags" + tags.toString());
    }

    public NoSuchTags() {

    }
}
