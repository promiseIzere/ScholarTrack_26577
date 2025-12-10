package com.scholartrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Request payload for registering a student to a specific village.
 */
public record StudentCreateDTO(
        @NotBlank(message = "fullName is required")
        String fullName,
        @NotNull(message = "villageId is required")
        UUID villageId
) {}

