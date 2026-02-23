package com.karot.mrs.backend.repositories;

import com.karot.mrs.backend.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    @Query("""
   SELECT s FROM Showtime s
   WHERE s.status = 'AVAIABLE' AND s.startTime <= :now
""")
    List<Showtime> findAllExpired(LocalDateTime now);

    @Query("""
SELECT COUNT(s) > 0
FROM Showtime s
WHERE s.room.id = :roomId
AND (:startTime < s.endTime AND :endTime > s.startTime)
""")
    boolean existsOverlappingShowtime(Long roomId, LocalDateTime startTime, LocalDateTime endTime);

    Optional<Showtime> findByIdAndRoomId(Long roomId,Long showtimeId);
}


