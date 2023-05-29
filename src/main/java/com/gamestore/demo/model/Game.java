package com.gamestore.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gamestore.demo.model.enums.Genre;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
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
public class Game {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, unique = true, columnDefinition = "VARCHAR(100)")
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10,2)")
    @NotNull(message = "Price cannot be empty")
    @DecimalMin("0.01")
    private Double price;

    @Column(name = "genre", nullable = false, columnDefinition = "VARCHAR(100)")
    @NotNull(message = "Genre cannot be empty")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(name = "single_player", columnDefinition = "VARCHAR(100)")
    @NotNull(message = "Please specify if the game is for single player or not")
    private Boolean singlePlayer;

    @Column(name = "multi_player", columnDefinition = "VARCHAR(100)")
    @NotNull(message = "Please specify if the game is for multiplayer or not")
    private Boolean multiPlayer;

    @Column(name = "release_date", nullable = false, columnDefinition = "DATE")
    @NotNull(message = "Release date cannot be empty")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @NotNull
    @Column(name = "last_updated", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated = new Date();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_user",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_platform",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id"))
    private Set<Platform> platforms = new HashSet<>();

    @NotNull(message = "Publisher cannot be empty")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @PrePersist
    protected void onCreate() {
        lastUpdated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = new Date();
    }
}
