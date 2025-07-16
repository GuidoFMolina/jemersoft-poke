package com.jemersoft.jemersoft_poke.controller;

import com.jemersoft.jemersoft_poke.dto.PokemonResponseDto;
import com.jemersoft.jemersoft_poke.service.IPokemonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PokemonController}.
 * <p>
 * These tests mock the {@link IPokemonService} to verify the controller's behavior
 * when saving a Pokémon and retrieving all stored Pokémon.
 */
class PokemonControllerTest {

    // Mock the service to isolate controller behavior
    private final IPokemonService pokemonService = Mockito.mock(IPokemonService.class);

    // Controller instance with mocked service
    private final PokemonController controller = new PokemonController(pokemonService);

    /**
     * Tests that the controller correctly saves a Pokémon by its name
     * and returns a response with status 200 and the expected Pokémon data.
     */
    @Test
    void savePokemon_ReturnsResponse() {
        String pokeName = "pikachu";

        // Mocked response DTO
        PokemonResponseDto responseDto = new PokemonResponseDto();
        responseDto.setId(1L);
        responseDto.setName(pokeName);

        // Setup mock to return response DTO when savePokemon is called
        when(pokemonService.savePokemon(any())).thenReturn(responseDto);

        // Call the controller method with the Pokémon name
        ResponseEntity<PokemonResponseDto> response = controller.savePokemon(pokeName);

        // Assert HTTP status is 200 OK
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(pokeName, response.getBody().getName());

        // Verify that service method was called once with any DTO
        verify(pokemonService).savePokemon(any());
    }

    /**
     * Tests that the controller correctly returns a list of stored Pokémon
     * and the response contains the expected number of Pokémon.
     */
    @Test
    void getAllPokemons_ReturnsList() {
        PokemonResponseDto pikachu = new PokemonResponseDto();
        pikachu.setId(1L);
        pikachu.setName("pikachu");
        pikachu.setImageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png");
        pikachu.setWeight(60);
        pikachu.setTypes(List.of("electric"));
        pikachu.setAbilities(List.of("static", "lightning-rod"));

        PokemonResponseDto charmander = new PokemonResponseDto();
        charmander.setId(2L);
        charmander.setName("charmander");
        charmander.setImageUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png");
        charmander.setWeight(85);
        charmander.setTypes(List.of("fire"));
        charmander.setAbilities(List.of("blaze", "solar-power"));

        List<PokemonResponseDto> mockList = List.of(pikachu, charmander);

        when(pokemonService.getAllPokemons()).thenReturn(mockList);

        ResponseEntity<List<PokemonResponseDto>> response = controller.getAllPokemons();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());

        // Opcional: validar un campo específico de cada elemento
        assertEquals("pikachu", response.getBody().get(0).getName());
        assertEquals("electric", response.getBody().get(0).getTypes().get(0));
        assertTrue(response.getBody().get(0).getAbilities().contains("static"));

        assertEquals("charmander", response.getBody().get(1).getName());
        assertEquals("fire", response.getBody().get(1).getTypes().get(0));
        assertTrue(response.getBody().get(1).getAbilities().contains("blaze"));

        verify(pokemonService).getAllPokemons();
    }
}
