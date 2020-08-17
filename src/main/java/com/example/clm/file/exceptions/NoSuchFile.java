package com.example.clm.file.exceptions;

import java.util.List;

/**
 * The type No such file.
 */
//@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such a file")
public class NoSuchFile extends RuntimeException {
    /**
     * Instantiates a new No such file.
     *
     * @param tags the tags
     */
    public NoSuchFile(List<String> tags) {
        super("No such file with id = " + tags);
    }
}
