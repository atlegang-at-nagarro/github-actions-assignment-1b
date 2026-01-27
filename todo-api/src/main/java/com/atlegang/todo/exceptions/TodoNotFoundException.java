package com.atlegang.todo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TodoNotFoundException extends ResponseStatusException {
    public TodoNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "To-do not found with id: " + id);
    }
}
