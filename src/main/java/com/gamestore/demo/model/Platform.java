package com.gamestore.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gamestore.demo.model.dto.PlatformDto;
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
                this.getId(),
                this.getName(),
                this.getDescription()
        );
    }

}
