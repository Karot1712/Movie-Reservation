package com.karot.mrs.backend.controller;

import com.karot.mrs.backend.dto.request.CreateReservationRequest;
import com.karot.mrs.backend.dto.response.ReservationResponse;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody CreateReservationRequest request) throws MrsException {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    @PostMapping("/{reservationId}/confirm")
    public ResponseEntity<ReservationResponse> confirmReservation(@PathVariable Long reservationId) throws MrsException {
        return ResponseEntity.ok(reservationService.confirmReservation(reservationId));
    }
}

