package com.karot.mrs.backend.repositories;

import com.karot.mrs.backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("""
    SELECT r.name 
    FROM Room r
    WHERE r.theater.id = :theaterId AND r.name IN :names
""")
    List<String> findExistingName(Long theaterId, List<String> names);

    Optional<Room> findByIdAndTheaterId(Long roomId, Long theaterId);
}
