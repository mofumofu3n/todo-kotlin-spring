package com.mofumofu.todoapp.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Entity
@Table(name = "todos")
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @field:NotBlank(message = "タスクの内容は必須です")
    @Column(nullable = false)
    var title: String,
    
    @Column
    var description: String? = null,
    
    @Column(nullable = false)
    var completed: Boolean = false,
    
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
)
