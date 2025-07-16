package com.jemersoft.jemersoft_poke.mapper;

import com.jemersoft.jemersoft_poke.dto.PokemonResponseDto;
import com.jemersoft.jemersoft_poke.model.Pokemon;

/**
 * Mapper utility for converting Pokémon entities to DTOs.
 */
public class PokemonMapper {

    private PokemonMapper() {}

    /**
     * Converts a Pokémon entity to a response DTO.
     *
     * @param pokemon the Pokémon entity
     * @return the response DTO
     */
    public static PokemonResponseDto toResponseDto(Pokemon pokemon) {
        return new PokemonResponseDto(
                pokemon.getId(),
                pokemon.getName(),
                pokemon.getImageUrl(),
                pokemon.getWeight(),
                pokemon.getTypes(),
                pokemon.getAbilities()
        );
    }
}
