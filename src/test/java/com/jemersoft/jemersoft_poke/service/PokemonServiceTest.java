package com.jemersoft.jemersoft_poke.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemersoft.jemersoft_poke.dto.PokemonRequestDto;
import com.jemersoft.jemersoft_poke.dto.PokemonResponseDto;
import com.jemersoft.jemersoft_poke.exception.PokemonAlreadyExistsException;
import com.jemersoft.jemersoft_poke.exception.PokemonNotFoundException;
import com.jemersoft.jemersoft_poke.model.Pokemon;
import com.jemersoft.jemersoft_poke.repository.PokemonRepository;
import com.jemersoft.jemersoft_poke.service.implementation.PokemonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PokemonServiceImpl} using MockitoExtension.
 * <p>
 * These tests mock dependencies such as {@link PokemonRepository} and {@link RestTemplate}
 * to validate service behavior for saving and retrieving Pokémon data.
 */
@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Tests successful saving of a Pokémon when it does not already exist in the database.
     * Mocks the external PokeAPI call and verifies the response DTO mapping.
     */
    @Test
    void savePokemon_Success() throws Exception {
        String pokeName = "pikachu";

        // Mock no Pokémon found in DB yet
        when(pokemonRepository.findByName(pokeName)).thenReturn(Optional.empty());

        // Mock PokeAPI JSON response
        String pokeApiResponse = """
            {
              "name": "pikachu",
              "sprites": {"front_default": "http://image.url/pikachu.png"},
              "weight": 60,
              "types": [{"type": {"name": "electric"}}],
              "abilities": [{"ability": {"name": "static"}}]
            }
            """;
        JsonNode pokeData = mapper.readTree(pokeApiResponse);
        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class)))
                .thenReturn(new ResponseEntity<>(pokeData, HttpStatus.OK));

        // Mock repository save returns input entity
        when(pokemonRepository.save(ArgumentMatchers.any(Pokemon.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Call service method
        PokemonResponseDto response = pokemonService.savePokemon(pokeName);

        // Validate response content
        assertNotNull(response);
        assertEquals(pokeName, response.getName());
        assertEquals("http://image.url/pikachu.png", response.getImageUrl());
        assertTrue(response.getTypes().contains("electric"));
        assertTrue(response.getAbilities().contains("static"));

        // Verify interactions
        verify(pokemonRepository).findByName(pokeName);
        verify(restTemplate).getForEntity(contains(pokeName), eq(JsonNode.class));
        verify(pokemonRepository).save(any(Pokemon.class));
    }

    /**
     * Tests that saving a Pokémon which already exists in the database
     * throws a {@link PokemonAlreadyExistsException}.
     */
    @Test
    void savePokemon_AlreadyExists_ThrowsException() {
        String pokeName = "pikachu";
        when(pokemonRepository.findByName(pokeName)).thenReturn(Optional.of(new Pokemon()));

        PokemonAlreadyExistsException ex = assertThrows(PokemonAlreadyExistsException.class,
                () -> pokemonService.savePokemon(pokeName));

        assertTrue(ex.getMessage().contains(pokeName));
        verify(pokemonRepository).findByName(pokeName);
        verifyNoMoreInteractions(restTemplate, pokemonRepository);
    }

    /**
     * Tests that saving a Pokémon which is not found in the external API
     * throws a {@link PokemonNotFoundException}.
     */
    @Test
    void savePokemon_NotFoundInApi_ThrowsException() {
        String pokeName = "unknownpoke";
        when(pokemonRepository.findByName(pokeName)).thenReturn(Optional.empty());
        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class)))
                .thenThrow(HttpClientErrorException.NotFound.create(null, null, null, null, null));

        PokemonNotFoundException ex = assertThrows(PokemonNotFoundException.class,
                () -> pokemonService.savePokemon(pokeName));

        assertTrue(ex.getMessage().contains(pokeName));
        verify(pokemonRepository).findByName(pokeName);
        verify(restTemplate).getForEntity(contains(pokeName), eq(JsonNode.class));
    }

    /**
     * Tests retrieving all Pokémon returns a list of {@link PokemonResponseDto}.
     */
    @Test
    void getAllPokemons_ReturnsList() {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(1L);
        pokemon.setName("pikachu");
        pokemon.setImageUrl("http://image.url/pikachu.png");
        pokemon.setWeight(60);
        pokemon.setTypes(List.of("electric"));
        pokemon.setAbilities(List.of("static"));

        when(pokemonRepository.findAll()).thenReturn(List.of(pokemon));

        List<PokemonResponseDto> pokemons = pokemonService.getAllPokemons();

        assertNotNull(pokemons);
        assertEquals(1, pokemons.size());
        assertEquals("pikachu", pokemons.get(0).getName());

        verify(pokemonRepository).findAll();
    }
}
