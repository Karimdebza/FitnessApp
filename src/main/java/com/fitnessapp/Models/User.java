package com.fitnessapp.Models;
import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;
    private String username;
    private String email;
    private String password;
    private Time create_at;

    public User(){}
}
