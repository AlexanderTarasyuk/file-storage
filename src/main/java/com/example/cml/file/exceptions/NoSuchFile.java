package com.example.cml.file.exceptions;

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
    public NoSuchFile(String tags) {
        super("No such file with id = " + tags);
    }
}
