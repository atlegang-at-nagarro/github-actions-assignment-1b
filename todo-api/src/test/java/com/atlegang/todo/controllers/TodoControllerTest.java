package com.atlegang.todo.controllers;

import com.atlegang.todo.dtos.TodoResponse;
import com.atlegang.todo.mappers.TodoMapper;
import com.atlegang.todo.models.Todo;
import com.atlegang.todo.repositories.TodoRepository;
import com.atlegang.todo.services.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoControllerTest {
    @Mock
    private TodoRepository todoRepository;

    @Mock
    private TodoService todoService;

    @Mock
    private TodoMapper todoMapper;

    @InjectMocks
    private TodoController todoController;

    @Test
    @DisplayName("GET /api/todos returns correct list size")
    void getAllTodos_ReturnsCorrectSize() {
        // Arrange
        Todo t1 = new Todo();
        t1.setId(1L);
        t1.setTitle("A");
        t1.setCompleted(false);

        Todo t2 = new Todo();
        t2.setId(2L);
        t2.setTitle("B");
        t2.setCompleted(true);

        Todo t3 = new Todo();
        t3.setId(3L);
        t3.setTitle("C");
        t3.setCompleted(false);

        when(todoRepository.findAll()).thenReturn(List.of(t1, t2, t3));

        // Act
        ResponseEntity<List<TodoResponse>> response = todoController.getAllTodos();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(3);

        verify(todoRepository).findAll();
        verifyNoInteractions(todoService, todoMapper);
    }

    @Test
    @DisplayName("GET /api/todos/{id} returns a single todo")
    void getTodoById_ReturnsSingleTodo() {
        Long id = 10L;

        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle("Read");
        todo.setCompleted(true);

        TodoResponse mapped = new TodoResponse(id, "Read", true);

        when(todoService.getTodoById(id)).thenReturn(todo);
        when(todoMapper.todoResponse(todo)).thenReturn(mapped);

        ResponseEntity<TodoResponse> response = todoController.getTodoById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isEqualTo(id);
        assertThat(response.getBody().title()).isEqualTo("Read");
        assertThat(response.getBody().completed()).isEqualTo(true);

        verify(todoService).getTodoById(id);
        verify(todoMapper).todoResponse(todo);
        verifyNoInteractions(todoRepository);


    }

}
