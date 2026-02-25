package com.karot.mrs.backend.repositories;

import com.karot.mrs.backend.dto.SeatStatus;
import com.karot.mrs.backend.entity.ShowtimeSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowtimeSeatRepository extends JpaRepository<ShowtimeSeat,Long> {
    List<ShowtimeSeat> findByShowtime_IdAndSeat_IdIn(Long showtimeId, List<Long> seatId);
    List<ShowtimeSeat> findAllByShowtimeId(Long showtimeId);
    List<ShowtimeSeat> findByReservationId(Long reservationId);
}
