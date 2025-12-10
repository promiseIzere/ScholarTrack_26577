package com.scholartrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

import com.scholartrack.model.Student.Status;

/**
 * Request payload for registering a student to a specific village.
 */
public record StudentCreateDTO(
        @NotBlank(message = "fullName is required")
        String fullName,
        @NotBlank(message = "email is required")
        String email,
        @NotBlank(message = "gender is required")
        String gender,
        @NotNull(message = "dateOfBirth is required")
        LocalDate dateOfBirth,
        @NotNull(message = "enrollmentDate is required")
        LocalDate enrollmentDate,
        @NotNull(message = "status is required")
        Status status,
        @NotNull(message = "villageId is required")
        UUID villageId
) {}

