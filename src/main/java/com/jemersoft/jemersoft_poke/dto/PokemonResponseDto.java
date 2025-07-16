package com.jemersoft.jemersoft_poke.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * Response DTO for a Pokémon.
 */
@Schema(description = "Response DTO containing Pokémon data.")
public class PokemonResponseDto {
    private Long id;
    private String name;
    private String imageUrl;
    private Integer weight;
    private List<String> types;
    private List<String> abilities;

    public PokemonResponseDto() {}

    public PokemonResponseDto(Long id, String name, String imageUrl, Integer weight, List<String> types, List<String> abilities) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.weight = weight;
        this.types = types;
        this.abilities = abilities;
    }

    // Getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }
    public List<String> getTypes() { return types; }
    public void setTypes(List<String> types) { this.types = types; }
    public List<String> getAbilities() { return abilities; }
    public void setAbilities(List<String> abilities) { this.abilities = abilities; }
}
