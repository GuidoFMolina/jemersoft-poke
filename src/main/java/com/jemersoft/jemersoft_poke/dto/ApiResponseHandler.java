package com.jemersoft.jemersoft_poke.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Generic API response wrapper that contains a message and data.
 *
 * @param <T> the type of the data object
 */
@Schema(description = "Generic API response containing a message and data")
public class ApiResponseHandler<T> {

    @Schema(description = "Response message", example = "Pokémon saved successfully")
    private String message;

    @Schema(description = "Response data")
    private T data;

    public ApiResponseHandler() {
    }

    public ApiResponseHandler(String message, T data) {
        this.message = message;
        this.data = data;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
