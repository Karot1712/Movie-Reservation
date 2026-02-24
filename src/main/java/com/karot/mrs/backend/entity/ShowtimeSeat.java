package com.karot.mrs.backend.entity;

import com.karot.mrs.backend.dto.SeatStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "showtime_seats", uniqueConstraints = @UniqueConstraint(columnNames = {"showtime_id", "seat_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowtimeSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus seatStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime lockedUntil;

    @Column(nullable = false)
    @Version
    private Long version;  // Optimistic locking for seat status updates

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public boolean isLockedExpired(LocalDateTime now) {
        return seatStatus == SeatStatus.LOCKED && lockedUntil != null && now.isAfter(lockedUntil);
    }

    public void lock(int minutes){
        this.seatStatus = SeatStatus.LOCKED;
        this.lockedUntil = LocalDateTime.now().plusMinutes(minutes);
    }

    public void releaseLock(){
        this.seatStatus = SeatStatus.AVAILABLE;
        this.lockedUntil = null;
    }

    public void markSold(){
        this.seatStatus = SeatStatus.SOLD;
        this.lockedUntil = null;
    }

}
