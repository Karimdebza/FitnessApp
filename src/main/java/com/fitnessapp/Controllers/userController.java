package com.fitnessapp.Controllers;

import com.fitnessapp.Models.User;
import com.fitnessapp.services.userService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")

public class userController {

    private final userService userService;

    public userController(userService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK); // 200 OK
    }

    @GetMapping("/{id_user}")
    public ResponseEntity<User> getUserById(@PathVariable int id_user) {
            Optional<User> user = userService.getUserById(id_user);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser =  userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id_user}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id_user) {
        try {
            userService.deleteUser(id_user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    @PutMapping("/{id_user}")
    public ResponseEntity<User> updateUser(@PathVariable int id_user, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id_user, userDetails);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK); // 200 OK
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }


}