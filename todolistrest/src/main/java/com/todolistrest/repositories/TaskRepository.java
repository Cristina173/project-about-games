package com.todolistrest.repositories;

import com.todolistrest.entities.Task;
import com.todolistrest.entities.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// extindem o interfata exista din spring care ne permite
// sa interactionam cu baza de date
// annotam clasa cu stereotype annotation @Repository pentru
// a o putea injecta in servicii -> TaskService.java
// C - create
// R - read
// U - update
// D - delete
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // genereaza un query SQL care returneaza toate task-urile bazate
    // pe task statusul trimis ca parameteru
    // select * from task where task.taskStatus = 'taskStatus'
    List<Task> findAllByTaskStatus(TaskStatus taskStatus);

    //select * from task where task.taskStatus = 'taskStatus' and task.id = 'id'
    //select * from task where task.taskStatus = COMPLETED and task.id = 5
    Task findByTaskStatusAndId(TaskStatus taskStatus, Long id);

    // select * from task where task.name like '%name%'
    List<Task> findAllByNameLike(String name);
}
