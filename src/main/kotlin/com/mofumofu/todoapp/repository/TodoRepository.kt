package com.mofumofu.todoapp.repository

import com.mofumofu.todoapp.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long>
