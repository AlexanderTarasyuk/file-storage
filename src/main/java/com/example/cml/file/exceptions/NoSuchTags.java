package com.example.cml.file.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * The type No such tags.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such tags")
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
