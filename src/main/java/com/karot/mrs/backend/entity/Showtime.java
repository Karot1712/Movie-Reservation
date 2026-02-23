package com.karot.mrs.backend.entity;

import com.karot.mrs.backend.dto.ShowtimeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shows", uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "startTime"}))
@Getter
@Setter
@Builder
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private ShowtimeStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Version
    private Long version;  // Optimistic locking version for concurrency control

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void close(){
        if(this.status == ShowtimeStatus.CLOSED){
            throw new IllegalStateException("Showtime is already closed");
        }
        this.status = ShowtimeStatus.CLOSED;
    }
}
