package com.atlegang.todo.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateTodoRequest(@NotBlank String title, Boolean completed) {}
