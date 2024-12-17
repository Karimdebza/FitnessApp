package  com.fitnessapp.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.fitnessapp.Models.User;
import com.fitnessapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public  class userService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public userService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public Optional<User> getUserById(int id_user) {
        return userRepository.findById(id_user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword){
     return passwordEncoder.matches(rawPassword, encodedPassword);
}

    public User updateUser(int id_user, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id_user);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Mise à jour des champs de l'utilisateur uniquement si nécessaire
            if (userDetails.getUsername() != null) {
                existingUser.setUsername(userDetails.getUsername());
            }
            if (userDetails.getEmail() != null) {
                existingUser.setEmail(userDetails.getEmail());
            }
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                // Hacher le mot de passe avant de l'enregistrer
                String hashedPassword = passwordEncoder.encode(userDetails.getPassword());
                existingUser.setPassword(hashedPassword);
            }

            // Enregistrer l'utilisateur mis à jour dans la base de données
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id " + id_user);
        }
    }

    public void deleteUser(int id_user) {
        userRepository.deleteById(id_user);
    }
}