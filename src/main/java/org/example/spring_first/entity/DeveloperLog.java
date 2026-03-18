package org.example.spring_first.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "developer_log", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "log_date"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @Column(name = "completed_modules", nullable = false, columnDefinition = "TEXT")
    private String completedModules;

    @Column(name = "active_blockers", columnDefinition = "TEXT")
    private String activeBlockers;

    @Column(name = "sprint_goals", nullable = false, columnDefinition = "TEXT")
    private String sprintGoals;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;
}