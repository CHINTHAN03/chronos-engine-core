package org.example.spring_first.service;

import lombok.RequiredArgsConstructor;
import org.example.spring_first.entity.DeveloperLog;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PresentationService {

    private final DeveloperLogService logService;
    private final AiSummarizationService aiService;

    public String generateReleaseNotes(UUID userId, LocalDate startDate, LocalDate endDate, String audience) {
        List<DeveloperLog> logs = logService.getLogsForReviewPeriod(userId, startDate, endDate);

        if (logs.isEmpty()) {
            return "No development logs found for this sprint period.";
        }

        StringBuilder rawData = new StringBuilder();
        for (DeveloperLog log : logs) {
            rawData.append("Completed: ").append(log.getCompletedModules()).append(". ");
            if (log.getActiveBlockers() != null && !log.getActiveBlockers().isEmpty()) {
                rawData.append("Blocker Resolved/Active: ").append(log.getActiveBlockers()).append(". ");
            }
        }

        return aiService.summarizeForExecutiveDeck(rawData.toString(), audience);
    }
}