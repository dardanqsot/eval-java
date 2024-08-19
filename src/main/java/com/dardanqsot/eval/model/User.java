package com.dardanqsot.eval.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "eval_user" )
public class User {

    @Id
    @GeneratedValue
    @Column(updatable = false, unique = true)
    private UUID idUser;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique=true)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDate created;

    private LocalDate modified;

    private LocalDate lastLogin;

    private String token;

    private boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones;

}
