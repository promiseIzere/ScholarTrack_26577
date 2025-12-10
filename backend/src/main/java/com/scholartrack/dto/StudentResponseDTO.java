package com.scholartrack.dto;

import java.util.UUID;

/**
 * Flattened response with the full location chain for a student.
 */
public record StudentResponseDTO(
        UUID id,
        String studentNumber,
        String fullName,
        String province,
        String district,
        String sector,
        String cell,
        String village
) {}

