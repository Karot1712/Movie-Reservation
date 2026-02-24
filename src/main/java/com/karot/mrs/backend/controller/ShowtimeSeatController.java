package com.karot.mrs.backend.controller;

import com.karot.mrs.backend.dto.request.SeatActionRequest;
import com.karot.mrs.backend.dto.response.ShowtimeSeatDto;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.service.ShowtimeSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/showtimes/{showtimeId}/seats")
@RequiredArgsConstructor
public class ShowtimeSeatController {
    private final ShowtimeSeatService showtimeSeatService;

    @PostMapping("/lock")
    public ResponseEntity<List<ShowtimeSeatDto>> lockSeats(@PathVariable Long showtimeId, @RequestBody SeatActionRequest seatIds) throws MrsException {
        return ResponseEntity.ok(showtimeSeatService.lockSeats(showtimeId, seatIds));
    }

    @GetMapping()
    public ResponseEntity<List<ShowtimeSeatDto>> getSeats(@PathVariable Long showtimeId) {
        return ResponseEntity.ok(showtimeSeatService.getSeats(showtimeId));
    }

    @PostMapping("/confirm")
    public void confirmSeats(@PathVariable Long showtimeId, @RequestBody SeatActionRequest seatIds) throws MrsException {
        showtimeSeatService.confirmSeats(showtimeId, seatIds);
    }

    @PostMapping("/release")
    public void releaseExpiredLocks(@PathVariable Long showtimeId){
        showtimeSeatService.releaseExpiredLocks(showtimeId);
    }
}
