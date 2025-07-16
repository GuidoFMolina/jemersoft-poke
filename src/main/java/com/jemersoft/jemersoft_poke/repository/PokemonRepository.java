package com.jemersoft.jemersoft_poke.repository;

import com.jemersoft.jemersoft_poke.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing Pokémon entities in the database.
 */
@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    /**
     * Finds a Pokémon by its name.
     *
     * @param name the Pokémon name
     * @return an Optional containing the Pokémon if found, or empty otherwise
     */
    Optional<Pokemon> findByName(String name);
}
