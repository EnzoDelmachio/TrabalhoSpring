package com.github.enzodelmachio.api.repository;


import com.github.enzodelmachio.api.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    // Método para buscar tasks futuras por usuário
    List<Task> findByUserIdAndDateAfter(Long userId, LocalDateTime date);
}