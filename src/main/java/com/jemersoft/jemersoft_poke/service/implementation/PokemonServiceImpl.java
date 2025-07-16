package com.jemersoft.jemersoft_poke.service.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.jemersoft.jemersoft_poke.dto.PokemonResponseDto;
import com.jemersoft.jemersoft_poke.exception.PokemonAlreadyExistsException;
import com.jemersoft.jemersoft_poke.exception.PokemonNotFoundException;
import com.jemersoft.jemersoft_poke.mapper.PokemonMapper;
import com.jemersoft.jemersoft_poke.model.Pokemon;
import com.jemersoft.jemersoft_poke.repository.PokemonRepository;
import com.jemersoft.jemersoft_poke.service.IPokemonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements IPokemonService {

    private static final Logger log = Logger.getLogger(PokemonServiceImpl.class.getName());

    private final PokemonRepository pokemonRepository;
    private final RestTemplate restTemplate;

    @Value("${pokeapi.base-url:https://pokeapi.co/api/v2}")
    private String pokeApiBaseUrl;

    public PokemonServiceImpl(PokemonRepository pokemonRepository, RestTemplate restTemplate) {
        this.pokemonRepository = pokemonRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public PokemonResponseDto savePokemon(String name) {
        // Check if Pokémon already exists
        Optional<Pokemon> existing = pokemonRepository.findByName(name);
        if (existing.isPresent()) {
            log.warning("Pokémon '" + name + "' already exists in the database.");
            throw new PokemonAlreadyExistsException(name);
        }

        // Fetch from PokeAPI
        String url = pokeApiBaseUrl + "/pokemon/" + name;
        JsonNode pokeData;
        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
            pokeData = response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            log.severe("Pokémon '" + name + "' not found in PokeAPI.");
            throw new PokemonNotFoundException(name);
        }

        // Map data to entity
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokeData.get("name").asText());
        pokemon.setImageUrl(pokeData.get("sprites").get("front_default").asText());
        pokemon.setWeight(pokeData.get("weight").asInt());
        pokemon.setTypes(extractList(pokeData.get("types"), "type"));
        pokemon.setAbilities(extractList(pokeData.get("abilities"), "ability"));

        // Save entity
        Pokemon saved = pokemonRepository.save(pokemon);
        log.info("Pokémon '" + name + "' saved successfully.");

        // Map entity to DTO
        return PokemonMapper.toResponseDto(saved);
    }

    @Override
    public List<PokemonResponseDto> getAllPokemons() {
        return pokemonRepository.findAll().stream()
                .map(PokemonMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    private List<String> extractList(JsonNode arrayNode, String innerKey) {
        List<String> list = new ArrayList<>();
        arrayNode.forEach(node -> {
            JsonNode inner = node.get(innerKey);
            if (inner != null && inner.get("name") != null) {
                list.add(inner.get("name").asText());
            }
        });
        return list;
    }
}
