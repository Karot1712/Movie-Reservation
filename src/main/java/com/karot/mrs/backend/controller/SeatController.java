package com.karot.mrs.backend.controller;

import com.karot.mrs.backend.dto.request.CreateSeatsRequest;
import com.karot.mrs.backend.dto.request.UpdateSeatTypeRequest;
import com.karot.mrs.backend.dto.response.SeatDto;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms/{roomId}/seats")
public class SeatController {
    private final SeatService seatService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SeatDto>>generateSeats(@PathVariable Long roomId, @RequestBody CreateSeatsRequest request) throws MrsException {
        return ResponseEntity.ok(seatService.generateSeats(roomId, request));
    }

    @GetMapping()
    public ResponseEntity<List<SeatDto>> getAllSeats(@PathVariable Long roomId) throws MrsException {
        return ResponseEntity.ok(seatService.getAllSeats(roomId));
    }

    @PatchMapping("/{seatId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SeatDto> updateSeatType(@PathVariable Long seatId, @RequestBody UpdateSeatTypeRequest request) throws MrsException {
        return ResponseEntity.ok(seatService.updateSeatType(seatId, request));
    }
}
