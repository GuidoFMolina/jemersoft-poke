package com.jemersoft.jemersoft_poke.exception;

/**
 * Exception thrown when a Pokémon already exists in the database.
 */
public class PokemonAlreadyExistsException extends RuntimeException {
    public PokemonAlreadyExistsException(String name) {
        super("Pokémon already exists: " + name);
    }
}