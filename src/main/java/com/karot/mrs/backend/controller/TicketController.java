package com.karot.mrs.backend.controller;

import com.karot.mrs.backend.dto.response.TicketResponse;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservations/{reservationId}/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getTickets(@PathVariable Long reservationId) throws MrsException {
        return ResponseEntity.ok(ticketService.getTicketsByReservation(reservationId));
    }
}

