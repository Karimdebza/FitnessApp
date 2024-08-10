package  com.fitnessapp.services;

import com.fitnessapp.Models.User;
import  com.fitnessapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public  class userService {

    private final UserRepository userRepository;

    public userService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  Optional<User> getUserById(int id_user) {
        return userRepository.findById(id_user);
    }
    public  List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
    public User updateUser(int id_user, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id_user);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Mise à jour des champs de l'utilisateur
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPassword(userDetails.getPassword());
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id " + id_user);
        }
    }

    public void deleteUser(int id_user) {
        userRepository.deleteById(id_user);
    }
}