package com.mofumofu.todoapp.controller

import com.mofumofu.todoapp.model.Todo
import com.mofumofu.todoapp.service.TodoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(private val todoService: TodoService) {

    @GetMapping
    fun getAllTodos(): ResponseEntity<List<Todo>> {
        val todos = todoService.getAllTodos()
        return ResponseEntity.ok(todos)
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: Long): ResponseEntity<Todo> {
        return todoService.getTodoById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun createTodo(@Valid @RequestBody todo: Todo): ResponseEntity<Todo> {
        val savedTodo = todoService.createTodo(todo)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTodo)
    }

    @PutMapping("/{id}")
    fun updateTodo(
        @PathVariable id: Long,
        @Valid @RequestBody todoDetails: Todo
    ): ResponseEntity<Todo> {
        return todoService.updateTodo(id, todoDetails)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long): ResponseEntity<Void> {
        return if (todoService.getTodoById(id).isPresent) {
            todoService.deleteTodo(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PatchMapping("/{id}/toggle")
    fun toggleTodoStatus(@PathVariable id: Long): ResponseEntity<Todo> {
        return todoService.toggleTodoStatus(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }
}
