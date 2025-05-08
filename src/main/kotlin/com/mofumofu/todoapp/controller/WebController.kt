package com.mofumofu.todoapp.controller

import com.mofumofu.todoapp.model.Todo
import com.mofumofu.todoapp.service.TodoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@Controller
class WebController(private val todoService: TodoService) {

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("todos", todoService.getAllTodos())
        model.addAttribute("newTodo", Todo(title = ""))
        return "todo/index"
    }

    @GetMapping("/todos/new")
    fun newTodoForm(model: Model): String {
        model.addAttribute("todo", Todo(title = ""))
        return "todo/form"
    }

    @PostMapping("/todos")
    fun createTodo(@Valid @ModelAttribute("todo") todo: Todo, bindingResult: BindingResult): String {
        if (bindingResult.hasErrors()) {
            return "todo/form"
        }
        todoService.createTodo(todo)
        return "redirect:/"
    }

    @GetMapping("/todos/{id}/edit")
    fun editTodoForm(@PathVariable id: Long, model: Model): String {
        val todo = todoService.getTodoById(id)
        if (todo.isPresent) {
            model.addAttribute("todo", todo.get())
            return "todo/form"
        }
        return "redirect:/"
    }

    @PostMapping("/todos/{id}")
    fun updateTodo(
        @PathVariable id: Long,
        @Valid @ModelAttribute("todo") todo: Todo,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) {
            return "todo/form"
        }
        todoService.updateTodo(id, todo)
        return "redirect:/"
    }

    @GetMapping("/todos/{id}/delete")
    fun deleteTodo(@PathVariable id: Long): String {
        todoService.deleteTodo(id)
        return "redirect:/"
    }

    @GetMapping("/todos/{id}/toggle")
    fun toggleTodoStatus(@PathVariable id: Long): String {
        todoService.toggleTodoStatus(id)
        return "redirect:/"
    }
}
