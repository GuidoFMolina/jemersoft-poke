package com.jemersoft.jemersoft_poke.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a Pokémon.
 */
@Entity
@Table(name = "pokemons")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    private Integer weight;

    @ElementCollection
    @CollectionTable(name = "pokemon_types", joinColumns = @JoinColumn(name = "pokemon_id"))
    @Column(name = "type")
    private List<String> types = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "pokemon_abilities", joinColumns = @JoinColumn(name = "pokemon_id"))
    @Column(name = "ability")
    private List<String> abilities = new ArrayList<>();

    public Pokemon() {}

    public Pokemon(Long id, String name, String imageUrl, Integer weight, List<String> types, List<String> abilities) {
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
