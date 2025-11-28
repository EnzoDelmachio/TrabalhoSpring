package com.github.enzodelmachio.api.controller;


import com.github.enzodelmachio.api.model.Task;
import com.github.enzodelmachio.api.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    // GET /api/tasks - Buscar todas as tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.findAll();
        return ResponseEntity.ok(tasks);
    }
    
    // GET /api/tasks/user/{userId} - Buscar tasks futuras por usuário
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getFutureTasksByUserId(@PathVariable Long userId) {
        List<Task> tasks = taskService.findFutureTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
    
    // POST /api/tasks/user/{userId} - Criar nova task para um usuário
    @PostMapping("/user/{userId}")
    public ResponseEntity<Task> createTask(@PathVariable Long userId, @RequestBody Task task) {
        Task savedTask = taskService.save(userId, task);
        return savedTask != null ? ResponseEntity.status(HttpStatus.CREATED).body(savedTask)
                                : ResponseEntity.badRequest().build();
    }
    
    // GET /api/tasks/{id} - Buscar task por ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.findById(id);
        return task.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    // PUT /api/tasks/{id} - Atualizar task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.update(id, task);
        return updatedTask != null ? ResponseEntity.ok(updatedTask)
                                  : ResponseEntity.notFound().build();
    }
    
    // DELETE /api/tasks/{id} - Deletar task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build()
                      : ResponseEntity.notFound().build();
    }
}