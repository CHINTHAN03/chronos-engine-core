package org.example.spring_first.service;

import lombok.RequiredArgsConstructor;
import org.example.spring_first.dto.LogRequest;
import org.example.spring_first.entity.AppUser;
import org.example.spring_first.entity.DeveloperLog;
import org.example.spring_first.repository.AppUserRepository;
import org.example.spring_first.repository.DeveloperLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeveloperLogService {

    private final DeveloperLogRepository logRepository;
    private final AppUserRepository userRepository;

    @Transactional
    public DeveloperLog createLog(UUID userId, LogRequest request) {

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found in the system."));

        if (logRepository.findByUserIdAndLogDate(userId, request.getLogDate()).isPresent()) {
            throw new IllegalStateException("A developer log already exists for this date.");
        }

        DeveloperLog log = DeveloperLog.builder()
                .user(user)
                .logDate(request.getLogDate())
                .completedModules(request.getCompletedModules())
                .activeBlockers(request.getActiveBlockers())
                .sprintGoals(request.getSprintGoals())
                .build();

        return logRepository.save(log);
    }

    @Transactional(readOnly = true)
    public List<DeveloperLog> getLogsForReviewPeriod(UUID userId, LocalDate startDate, LocalDate endDate) {

        return logRepository.findByUserIdAndLogDateBetweenOrderByLogDateAsc(userId, startDate, endDate);
    }
}