package com.github.enzodelmachio.api.service;


import com.github.enzodelmachio.api.model.User;
import com.github.enzodelmachio.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Buscar todos os usuários
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
    
    // Buscar usuário por ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    // Criar novo usuário
    public User save(User user) {
        return userRepository.save(user);
    }
    
    // Atualizar usuário
    public User update(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return userRepository.save(user);
        }
        return null;
    }
    
    // Deletar usuário
    public boolean deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
