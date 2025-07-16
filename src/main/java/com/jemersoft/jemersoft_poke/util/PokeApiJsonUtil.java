package com.jemersoft.jemersoft_poke.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for extracting data from PokeAPI JSON responses.
 */
public class PokeApiJsonUtil {

    private PokeApiJsonUtil() {}

    /**
     * Extracts a list of strings (types or abilities) from the given PokeAPI array node.
     *
     * @param arrayNode the array node from PokeAPI JSON
     * @param innerKey the key to extract (e.g. "type" or "ability")
     * @return a list of names (types or abilities)
     */
    public static List<String> extractList(JsonNode arrayNode, String innerKey) {
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
