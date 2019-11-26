package com.apress.todo.repository;

import com.apress.todo.domain.ToDo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ToDoRepository extends ReactiveMongoRepository<ToDo, String> {
}
