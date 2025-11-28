package com.github.enzodelmachio.api.service;


import com.github.enzodelmachio.api.model.*;
import com.github.enzodelmachio.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // Buscar todas as tasks
    public List<Task> findAll() {
        return (List<Task>) taskRepository.findAll();
    }
    
    // Buscar tasks futuras por usuário
    public List<Task> findFutureTasksByUserId(Long userId) {
        return taskRepository.findByUserIdAndDateAfter(userId, LocalDateTime.now());
    }
    
    // Criar nova task
    public Task save(Long userId, Task task) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            task.setUser(user.get());
            return taskRepository.save(task);
        }
        return null;
    }
    
    // Buscar task por ID
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }
    
    // Atualizar task
    public Task update(Long id, Task taskDetails) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setName(taskDetails.getName());
            task.setDescription(taskDetails.getDescription());
            task.setDate(taskDetails.getDate());
            return taskRepository.save(task);
        }
        return null;
    }
    
    // Deletar task
    public boolean deleteById(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}