package com.karot.mrs.backend.repositories;

import com.karot.mrs.backend.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    List<Theater> findByNameContainingIgnoreCase(String name);
    Optional<Theater> findByRooms_Id(Long roomId);
}

