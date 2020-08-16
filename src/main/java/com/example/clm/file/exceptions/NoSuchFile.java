package com.example.clm.file.exceptions;

/**
 * The type No such file.
 */
//@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such a file")
public class NoSuchFile extends RuntimeException {
    /**
     * Instantiates a new No such file.
     *
     * @param id the id
     */
    public NoSuchFile(int id) {
        super("No such file with id = " + id);
    }
}
