package com.jemersoft.jemersoft_poke.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for storing a Pokémon by name.
 */
@Schema(description = "Request DTO to store a Pokémon by name. Only the name field is required.")
public class PokemonRequestDto {

    @Schema(
            description = "Pokémon name (case-insensitive, only letters, numbers and hyphens).",
            example = "pikachu",
            required = true
    )
    @NotBlank(message = "Pokemon name must not be blank")
    @Size(min = 1, max = 50, message = "Pokemon name must be between 1 and 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9\\-]+$",
            message = "Pokemon name can only contain letters, numbers, and hyphens"
    )
    private String name;

    // 👇 IMPORTANTE: constructor vacío manual, SIN Lombok
    public PokemonRequestDto() {}

    public PokemonRequestDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
