package com.karot.mrs.backend.repositories;

import com.karot.mrs.backend.dto.ReservationStatus;
import com.karot.mrs.backend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByReservationStatusAndExpireAtBefore(ReservationStatus status, LocalDateTime before);
}

