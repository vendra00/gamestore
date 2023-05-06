package com.gamestore.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlatformDto {

    @JsonIgnore
    private Long id;
    private String name;
    private String description;
}
