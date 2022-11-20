package com.todolistrest.controllers;

import com.todolistrest.entities.Task;
import com.todolistrest.entities.TaskStatus;
import com.todolistrest.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * POST - CREATE
 * GET - READ
 * PUT - UPDATE
 * DELETE - DELETE
 *
 *
 *
 * {
 *  "name" : "test",
 *  "description" : "some-description",
 *  "status" : "COMPLETED"
 * }
 */

@RestController // annotare specifica serviciilor REST
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/tasks")
    // @RequestBody - annotare care transforma JSON-ul trimis de utilizator
    // intr-un obiect java : proces de deserializare (mesaj -> obiect java);
    public Task save(@RequestBody Task task) {
        return taskService.save(task);
    }

    @GetMapping(value = "/tasks")
    public List<Task> findAll() {
        return taskService.findAll();
    }


    @GetMapping(value = "/tasks/{id}")
    public Task findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    // DELETE http://localhost:8080/tasks/1 ---> sterge task-ul cu id-ul 1
    // DELETE http://localhost:8080/tasks/2 ---> sterge task-ul cu id-ul 2

    // clasa ResponseEntity poate fi folosita atunci cand
    // vrem sa intoarcem un HTTP code inapoi catre client (clinetul fiind fie
    // o aplicatie de frontend sau postman)
    @DeleteMapping(value = "/tasks/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        taskService.delete(id);
        ResponseEntity response = new ResponseEntity(HttpStatus.NO_CONTENT);
        return response;
    }

    @PutMapping(value = "/tasks/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        return taskService.update(id, task);
    }

//    "/tasks/search?taskStatus=COMPLETED"
//   "/tasks/search?taskStatus=NOT_STARTED"
    @GetMapping(value = "/tasks/search")
    public List<Task> findTasksByTaskStatus(@RequestParam(required = false) TaskStatus taskStatus) {
        if (taskStatus == null) {
            return taskService.findAll();
        }
        return taskService.findAllByTaskStatus(taskStatus);
    }
}
