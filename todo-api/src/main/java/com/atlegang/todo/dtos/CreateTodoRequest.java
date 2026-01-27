package com.atlegang.todo.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoRequest(Long id, @NotBlank String title, Boolean completed) {}
