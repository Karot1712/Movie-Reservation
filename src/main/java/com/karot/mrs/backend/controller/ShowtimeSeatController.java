package com.karot.mrs.backend.controller;

import com.karot.mrs.backend.dto.response.ShowtimeSeatDto;
import com.karot.mrs.backend.service.ShowtimeSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/showtimes/{showtimeId}/seats")
@RequiredArgsConstructor
public class ShowtimeSeatController {
    private final ShowtimeSeatService showtimeSeatService;

    @GetMapping
    public ResponseEntity<List<ShowtimeSeatDto>> getSeats(@PathVariable Long showtimeId) {
        return ResponseEntity.ok(showtimeSeatService.getSeats(showtimeId));
    }
}
