package com.example.clm.file.exceptions;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such a file")
public class NoSuchFile extends RuntimeException {
    public NoSuchFile(int id) {
        super("No such file with id = " + id);
    }
}
