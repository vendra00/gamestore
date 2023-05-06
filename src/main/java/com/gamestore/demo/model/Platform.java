package com.gamestore.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gamestore.demo.controller.dto.PlatformDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Platform {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @JsonBackReference
    @ManyToMany(mappedBy = "platforms", fetch = FetchType.LAZY)
    private Set<Game> games = new HashSet<>();

    public PlatformDto toDto() {
        return new PlatformDto(
                this.getName(),
                this.getDescription()
        );
    }

}
