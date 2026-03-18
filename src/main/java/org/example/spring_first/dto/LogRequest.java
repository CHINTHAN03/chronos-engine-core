package org.example.spring_first.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LogRequest {

    @NotNull(message = "Log date is required")
    private LocalDate logDate;

    @NotBlank(message = "Completed modules cannot be empty")
    private String completedModules;

    private String activeBlockers; // This can be null/empty if there are no blockers

    @NotBlank(message = "Sprint goals are required")
    private String sprintGoals;
}