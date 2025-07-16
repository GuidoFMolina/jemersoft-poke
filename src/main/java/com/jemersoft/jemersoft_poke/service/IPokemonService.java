package com.jemersoft.jemersoft_poke.service;

import com.jemersoft.jemersoft_poke.dto.PokemonRequestDto;
import com.jemersoft.jemersoft_poke.dto.PokemonResponseDto;

import java.util.List;

/**
 * Service interface for Pokémon operations.
 */
public interface IPokemonService {

    /**
     * Saves a new Pokémon fetched from PokeAPI by name.
     *
     * @param name the string containing the Pokémon name
     * @return the saved Pokémon as a response DTO
     */
    PokemonResponseDto savePokemon(String name);

    /**
     * Returns a list of all stored Pokémon.
     *
     * @return a list of Pokémon response DTOs
     */
    List<PokemonResponseDto> getAllPokemons();
}
