package org.example.spring_first.controller;

import lombok.RequiredArgsConstructor;
import org.example.spring_first.service.PresentationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class PresentationController {

    private final PresentationService presentationService;

    @GetMapping("/generate/{userId}")
    public ResponseEntity<java.util.Map<String, String>> generateReport(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String audience) { // <-- Added audience parameter

        String releaseNotes = presentationService.generateReleaseNotes(userId, startDate, endDate, audience);

        return ResponseEntity.ok(java.util.Map.of("content", releaseNotes));
    }
}