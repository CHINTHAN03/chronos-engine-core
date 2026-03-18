package org.example.spring_first.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.spring_first.dto.LogRequest;
import org.example.spring_first.entity.DeveloperLog;
import org.example.spring_first.service.DeveloperLogService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class DeveloperLogController {

    private final DeveloperLogService logService;

    @PostMapping("/{userId}")
    public ResponseEntity<DeveloperLog> createLog(
            @PathVariable UUID userId,
            @Valid @RequestBody LogRequest request) {

        DeveloperLog createdLog = logService.createLog(userId, request);
        return new ResponseEntity<>(createdLog, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<DeveloperLog>> getLogsForReviewPeriod(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<DeveloperLog> logs = logService.getLogsForReviewPeriod(userId, startDate, endDate);
        return ResponseEntity.ok(logs);
    }
}