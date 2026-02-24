package com.karot.mrs.backend.repositories;

import com.karot.mrs.backend.dto.SeatStatus;
import com.karot.mrs.backend.entity.ShowtimeSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowtimeSeatRepository extends JpaRepository<ShowtimeSeat,Long> {
    List<ShowtimeSeat> findByShowtimeIdAndSeatStatus(Long showtimeId, SeatStatus seatStatus);
    List<ShowtimeSeat> findAllByShowtimeIdAndSeatId(Long showtimeId, List<Long> seatId);
    List<ShowtimeSeat> findAllByShowtimeId(Long showtimeId);
}
