package com.todo

import org.junit.Test
import org.mockito.InjectMocks

import org.assertj.core.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class TodoServiceTest {

    @Mock
    lateinit var todoRepository: TodoRepository;

    @InjectMocks
    lateinit var todoService : TodoService

    @Test
    fun `get all ToDos`() {

        val task1 = Todo(1, Date(), "Tech Talk", "Yeah!")
        val task2 = Todo(2, Date(), "Dinner", "With Friends")

        given(todoRepository.findAll()).willReturn(listOf(task1, task2))

        val todos = this.todoService.getTodos()

        verify(todoRepository, times(1)).findAll()

        assertThat(todos).isNotNull
        assertThat(todos).hasSize(2)
    }

}