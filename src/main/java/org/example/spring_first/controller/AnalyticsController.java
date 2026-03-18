package org.example.spring_first.controller;

import lombok.RequiredArgsConstructor;
import org.example.spring_first.entity.DeveloperLog;
import org.example.spring_first.service.DeveloperLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final DeveloperLogService logService;

    @GetMapping("/activity/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getActivityData(@PathVariable UUID userId) {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);

        List<DeveloperLog> logs = logService.getLogsForReviewPeriod(userId, startDate, endDate);

        List<Map<String, Object>> chartData = logs.stream().map(log -> {

            int impactScore = log.getCompletedModules() != null ? log.getCompletedModules().length() / 5 : 0;

            return Map.<String, Object>of(
                    "date", log.getLogDate().toString().substring(5), // Just get MM-DD
                    "impact", Math.max(impactScore, 5) // Minimum score of 5 if a log exists
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(chartData);
    }
}