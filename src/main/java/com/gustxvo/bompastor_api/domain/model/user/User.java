package com.gustxvo.bompastor_api.domain.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;
}
