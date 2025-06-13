package com.mofumofu.todoapp.controller

import com.mofumofu.todoapp.model.Todo
import com.mofumofu.todoapp.service.TodoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import java.time.LocalDate

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
    fun createTodo(@Valid @ModelAttribute("todo") todo: Todo, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("todo", todo)
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
        @RequestParam title: String,
        @RequestParam(required = false) description: String?,
        @RequestParam(required = false) completed: Boolean?,
        @RequestParam(required = false) registrationDate: String?,
        @RequestParam(required = false) startDate: String?,
        @RequestParam(required = false) expectedEndDate: String?,
        @RequestParam(required = false) actualEndDate: String?,
        model: Model
    ): String {
        println("DEBUG: Received todo update for ID $id")
        println("DEBUG: Title: $title")
        println("DEBUG: Registration date string: '$registrationDate'")
        println("DEBUG: Start date string: '$startDate'")
        println("DEBUG: Expected end date string: '$expectedEndDate'")
        println("DEBUG: Actual end date string: '$actualEndDate'")
        
        try {
            val existingTodo = todoService.getTodoById(id)
            if (existingTodo.isEmpty) {
                return "redirect:/"
            }
            
            val updatedTodo = existingTodo.get().copy(
                title = title,
                description = description,
                completed = completed ?: false,
                registrationDate = registrationDate?.takeIf { it.isNotBlank() }?.let { LocalDate.parse(it) },
                startDate = startDate?.takeIf { it.isNotBlank() }?.let { LocalDate.parse(it) },
                expectedEndDate = expectedEndDate?.takeIf { it.isNotBlank() }?.let { LocalDate.parse(it) },
                actualEndDate = actualEndDate?.takeIf { it.isNotBlank() }?.let { LocalDate.parse(it) }
            )
            
            println("DEBUG: Parsed dates successfully:")
            println("DEBUG: Registration date: ${updatedTodo.registrationDate}")
            println("DEBUG: Start date: ${updatedTodo.startDate}")
            println("DEBUG: Expected end date: ${updatedTodo.expectedEndDate}")
            println("DEBUG: Actual end date: ${updatedTodo.actualEndDate}")
            
            todoService.updateTodo(id, updatedTodo)
            return "redirect:/"
        } catch (e: Exception) {
            println("DEBUG: Error parsing dates: ${e.message}")
            model.addAttribute("error", "日付の形式が正しくありません")
            val todo = todoService.getTodoById(id)
            if (todo.isPresent) {
                model.addAttribute("todo", todo.get())
            }
            return "todo/form"
        }
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
