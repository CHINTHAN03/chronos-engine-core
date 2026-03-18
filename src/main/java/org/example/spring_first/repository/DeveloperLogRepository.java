package org.example.spring_first.repository;

import org.example.spring_first.entity.DeveloperLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeveloperLogRepository extends JpaRepository<DeveloperLog, UUID> {

    List<DeveloperLog> findByUserIdOrderByLogDateDesc(UUID userId);

    Optional<DeveloperLog> findByUserIdAndLogDate(UUID userId, LocalDate logDate);


    List<DeveloperLog> findByUserIdAndLogDateBetweenOrderByLogDateAsc(UUID userId, LocalDate startDate, LocalDate endDate);
}