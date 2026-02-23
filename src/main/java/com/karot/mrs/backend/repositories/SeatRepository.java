package com.karot.mrs.backend.repositories;

import com.karot.mrs.backend.entity.Room;
import com.karot.mrs.backend.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository  extends JpaRepository<Seat ,Long> {
    List<Seat> findByRoom(Room room);
}
