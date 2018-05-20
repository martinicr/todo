package com.todo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@SpringBootApplication
class TodoApplication

fun main(args: Array<String>) {
    runApplication<TodoApplication>(*args)
}


enum class TaskStatus {
    NOT_STARTED,
    IN_PROGRESS,
    DONE
}

@Entity
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val created: Date? = null,
    val title: String? = null,
    val summary: String? = null,
    val status: TaskStatus? = TaskStatus.NOT_STARTED
) : Serializable

@Repository
interface TodoRepository : JpaRepository<Todo, Long> {

}

@Service
class TodoService(val todoRepository : TodoRepository) {

    fun getTodos() : List<Todo> {
        return this.todoRepository.findAll()
    }

    fun addTask(todo: Todo) {
        this.todoRepository.save(todo)
    }

}

@RestController
@RequestMapping("/todos")
class TodoController(val todoService : TodoService) {

    @GetMapping("", "/")
    fun allTodos() : List<Todo> {
        return this.todoService.getTodos()
    }

    @PostMapping("", "/")
    fun addTask(@RequestBody newTodo : Todo){
        this.todoService.addTask(newTodo)
    }

}