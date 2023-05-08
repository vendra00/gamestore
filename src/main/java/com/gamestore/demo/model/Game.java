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

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Price cannot be empty")
    @DecimalMin("0.01")
    private Double price;

    @NotNull(message = "Genre cannot be empty")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @NotNull(message = "Please specify if the game is for single player or not")
    private Boolean singlePlayer;

    @NotNull(message = "Please specify if the game is for multiplayer or not")
    private Boolean multiPlayer;

    @NotNull(message = "Release date cannot be empty")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated = new Date();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "game_platform",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id"))
    private Set<Platform> platforms = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
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
