package com.scholartrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Payload for creating a location node within the hierarchy.
 */
public record LocationCreateDTO(
        @NotBlank(message = "name is required")
        String name,
        @NotBlank(message = "code is required")
        @Pattern(regexp = "^[A-Z0-9_-]{2,20}$", message = "code must be 2-20 uppercase letters, numbers, - or _")
        String code,
        String parentCode
) {}

