package com.mofumofu.todoapp.service

import com.mofumofu.todoapp.model.Todo
import com.mofumofu.todoapp.repository.TodoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class TodoService(private val todoRepository: TodoRepository) {

    fun getAllTodos(): List<Todo> = todoRepository.findAll()

    fun getTodoById(id: Long): Optional<Todo> = todoRepository.findById(id)

    @Transactional
    fun createTodo(todo: Todo): Todo = todoRepository.save(todo)

    @Transactional
    fun updateTodo(id: Long, todoDetails: Todo): Optional<Todo> {
        return todoRepository.findById(id).map { existingTodo ->
            existingTodo.title = todoDetails.title
            existingTodo.description = todoDetails.description
            existingTodo.completed = todoDetails.completed
            existingTodo.registrationDate = todoDetails.registrationDate
            existingTodo.startDate = todoDetails.startDate
            existingTodo.expectedEndDate = todoDetails.expectedEndDate
            existingTodo.actualEndDate = todoDetails.actualEndDate
            existingTodo.updatedAt = LocalDateTime.now()
            todoRepository.save(existingTodo)
        }
    }

    @Transactional
    fun deleteTodo(id: Long) {
        todoRepository.deleteById(id)
    }

    @Transactional
    fun toggleTodoStatus(id: Long): Optional<Todo> {
        return todoRepository.findById(id).map { todo ->
            todo.completed = !todo.completed
            todo.updatedAt = LocalDateTime.now()
            todoRepository.save(todo)
        }
    }
}
