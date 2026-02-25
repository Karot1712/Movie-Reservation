package com.karot.mrs.backend.repositories;

import com.karot.mrs.backend.entity.TicketSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketSeatRepository extends JpaRepository<TicketSeat, Long> {

    List<TicketSeat> findByTicket_Id(UUID ticketId);
}

