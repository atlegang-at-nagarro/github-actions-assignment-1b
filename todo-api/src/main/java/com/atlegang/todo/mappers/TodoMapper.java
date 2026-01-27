package com.atlegang.todo.mappers;

import com.atlegang.todo.dtos.CreateTodoRequest;
import com.atlegang.todo.dtos.TodoResponse;
import com.atlegang.todo.models.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {
    public TodoResponse todoResponse(Todo todo) {
        return new TodoResponse(todo.getId(), todo.getTitle(), todo.getCompleted());
    }

    public Todo toEntity(CreateTodoRequest request) {
        Todo todo = new Todo();
        todo.setTitle(request.title());
        todo.setCompleted(false);
        return todo;
    }
}
