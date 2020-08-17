package com.example.cml.file.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * The type Api error.
 */
@Data
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    /**
     * Instantiates a new Api error.
     *
     * @param status the status
     */
    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    /**
     * Instantiates a new Api error.
     *
     * @param status the status
     * @param ex     the ex
     */
    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    /**
     * Instantiates a new Api error.
     *
     * @param status  the status
     * @param message the message
     * @param ex      the ex
     */
    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}