package com.atlegang.todo.controllers;

import com.atlegang.todo.dtos.CreateTodoRequest;
import com.atlegang.todo.dtos.TodoResponse;
import com.atlegang.todo.dtos.UpdateTodoRequest;
import com.atlegang.todo.mappers.TodoMapper;
import com.atlegang.todo.models.Todo;
import com.atlegang.todo.repositories.TodoRepository;
import com.atlegang.todo.services.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
public class TodoController {

    private TodoRepository todoRepository;
    private TodoService todoService;
    private final TodoMapper todoMapper;

    @GetMapping()
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        List<TodoResponse> todos = todoRepository.findAll()
                .stream()
                .map(todo -> new TodoResponse(todo.getId(), todo.getTitle(), todo.getCompleted()))
                .toList();

        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id) {
        Todo todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todoMapper.todoResponse(todo));
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@RequestBody CreateTodoRequest request) {
        Todo todo = todoMapper.toEntity(request);
        Todo saved = todoRepository.save(todo);
        return ResponseEntity.ok(todoMapper.todoResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable Long id,
            @RequestBody UpdateTodoRequest request
            ) {
        Todo updated = todoService.updateTodoDetails(id, request);
        return ResponseEntity.ok(todoMapper.todoResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodo(@PathVariable Long id) {
        Todo todo = todoService.getTodoById(id);
        todoRepository.delete(todo);
        return ResponseEntity.noContent().build();
    }
}
