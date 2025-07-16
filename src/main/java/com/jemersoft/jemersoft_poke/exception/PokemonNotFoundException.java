package com.jemersoft.jemersoft_poke.exception;

/**
 * Exception thrown when a Pokémon is not found in the database or PokeAPI.
 */
public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String name) {
        super("Pokémon not found: " + name);
    }
}