package com.example.clm.file.exceptions;

import java.util.ArrayList;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such tags")
public class NoSuchTags extends RuntimeException {
    public NoSuchTags(ArrayList<String> tags) {
        super("No match tags" + tags.toString());
    }
}
