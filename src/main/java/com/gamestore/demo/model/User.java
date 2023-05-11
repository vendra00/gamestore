package com.gamestore.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Email cannot be empty")
    @Column(unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Please specify if the user is an admin or not")
    private Boolean isAdmin;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Game> games = new HashSet<>();

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated = new Date();

    @PrePersist
    protected void onCreate() {
        lastUpdated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = new Date();
    }
}

