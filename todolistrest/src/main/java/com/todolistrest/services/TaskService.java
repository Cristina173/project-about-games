package com.todolistrest.services;

import com.todolistrest.controllers.TaskController;
import com.todolistrest.entities.Task;
import com.todolistrest.entities.TaskStatus;
import com.todolistrest.exceptions.ResourceNotFoundException;
import com.todolistrest.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        List<Task> taskList = taskRepository.findAll();
        taskList.forEach(el ->
                el.setName(el.getName().toUpperCase(Locale.ROOT)));
        return taskList;
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public Task update(Long id, Task task) {
        Optional<Task> optionalDbTask = taskRepository.findById(id);
        // daca obiectul exista in db
        if (optionalDbTask.isPresent()) {
            // il laum din Optional
            Task dbTask = optionalDbTask.get();
            // setam noile valori
            dbTask.setName(task.getName());
            dbTask.setDescription(task.getDescription());
            dbTask.setTaskStatus(task.getTaskStatus());
            return taskRepository.save(dbTask);
        }
                                    // d -> digit
                                    // formateaza string-ul in asa fel incat sa faca replace la %d cu variabila id
        throw new ResourceNotFoundException(String.format("Resource with id %d does not exist", id));
    }

    public Task findById(Long id) {
        Optional<Task> taskById = taskRepository.findById(id);
        if (taskById.isPresent()) {
            return taskById.get();
        }
        throw new ResourceNotFoundException(String.format("Resource with id %d does not exist", id));
    }

    public Task findByIdJava8(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("Resource with id %d does not exist", id));
        });
    }

    public List<Task> findAllByTaskStatus(TaskStatus taskStatus) {
        return taskRepository.findAllByTaskStatus(taskStatus);
    }

    /**
     *         UI
     *    |        ^
     *    V        |
     * TaskController
     *    |       ^
     *    v       |
     * TaskService (taskServiceul mai poate face ceva proceare)
     *    |      ^
     *    v      |
     * TaskRepository
     *   |      ^
     *   v      |
     *   DB (select * from task where id = 1)
     *
     */
}
