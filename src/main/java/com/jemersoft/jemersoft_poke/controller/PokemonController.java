package com.jemersoft.jemersoft_poke.controller;

import com.jemersoft.jemersoft_poke.dto.ApiResponseHandler;
import com.jemersoft.jemersoft_poke.dto.PokemonResponseDto;
import com.jemersoft.jemersoft_poke.service.IPokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Pokémon operations (store and list).
 */
@RestController
@RequestMapping("/pokemon")
@Tag(name = "Pokémon API", description = "Endpoints to store and retrieve Pokémon from the database")
public class PokemonController {
    private static final Logger log = LoggerFactory.getLogger(PokemonController.class);

    private final IPokemonService pokemonService;

    public PokemonController(IPokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    /**
     * Stores a Pokémon fetched from PokeAPI by name.
     *
     * @param name The Pokémon name to fetch and save
     * @return ApiResponse containing message and saved Pokémon data
     */
    @Operation(
            summary = "Store a Pokémon from PokeAPI by name",
            description = "Fetches a Pokémon from PokeAPI using the provided name path variable and saves it in the local database.",
            parameters = {
                    @Parameter(
                            name = "name",
                            description = "The Pokémon name to fetch and save",
                            required = true,
                            in = ParameterIn.PATH,
                            example = "pikachu"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pokémon stored successfully",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input or validation error",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pokémon not found in PokeAPI",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Pokémon already exists",
                            content = @Content
                    )
            }
    )
    @PostMapping("/{name}")
    public ResponseEntity<ApiResponseHandler<PokemonResponseDto>> savePokemon(@PathVariable String name) {
        log.info("Execute endpoint: {}", "/pokemon/{name}");
        log.info("Received request to save Pokémon: {}", name);

        PokemonResponseDto saved = pokemonService.savePokemon(name);

        ApiResponseHandler<PokemonResponseDto> response = new ApiResponseHandler<>(
                "Pokémon saved successfully",
                saved
        );

        return ResponseEntity.ok(response);
    }


    /**
     * Retrieves all stored Pokémon from the local database.
     *
     * @return a list of Pokémon response DTOs
     */
    @Operation(
            summary = "List all stored Pokémon",
            description = "Returns a list of all Pokémon currently stored in the local database.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of stored Pokémon",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PokemonResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<PokemonResponseDto>> getAllPokemons() {
        log.info("Execute endpoint: {}", "/pokemon");
        log.info("Received request to list all stored Pokémon.");
        List<PokemonResponseDto> pokemons = pokemonService.getAllPokemons();
        return ResponseEntity.ok(pokemons);
    }
}
