package com.github.enzodelmachio.api.repository;


import com.github.enzodelmachio.api.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    // CrudRepository já fornece métodos básicos
}