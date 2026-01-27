package com.atlegang.todo.services;

import com.atlegang.todo.dtos.UpdateTodoRequest;
import com.atlegang.todo.exceptions.TodoNotFoundException;
import com.atlegang.todo.models.Todo;
import com.atlegang.todo.repositories.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public Todo getTodoById(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException(todoId));
    }

    @Transactional
    public Todo updateTodoDetails(Long todoId, UpdateTodoRequest request) {
        Todo todo = getTodoById(todoId);

        if (request.title() != null) {
            todo.setTitle(request.title());
        }

        if (request.completed() != null) {
            todo.setCompleted(request.completed());
        }
        return todo;
    }
}

