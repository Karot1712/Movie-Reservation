package com.karot.mrs.backend.controller;

import com.karot.mrs.backend.dto.request.CreateShowtimeRequest;
import com.karot.mrs.backend.dto.request.UpdateShowtimeRequest;
import com.karot.mrs.backend.dto.response.ShowtimeDto;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rooms/{roomId}/showtimes")
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ShowtimeDto> createShowtime(@Validated @PathVariable Long roomId, @RequestBody CreateShowtimeRequest request) throws MrsException {
        return new ResponseEntity<>(showtimeService.createShowtime(roomId,request), HttpStatus.CREATED);

    }

    @PutMapping("/{showtimeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ShowtimeDto> updateShowtime(@Validated @PathVariable Long roomId, @PathVariable Long showtimeId, @RequestBody UpdateShowtimeRequest request) throws MrsException {
        return new ResponseEntity<>(showtimeService.updateShowtime(roomId, showtimeId, request), HttpStatus.OK);
    }

    @GetMapping("/{showtimeId}")
    public ResponseEntity<ShowtimeDto> getShowtimeById(@PathVariable Long roomId, @PathVariable Long showtimeId) throws MrsException {
        return new ResponseEntity<>(showtimeService.getShowtimeById(roomId, showtimeId), HttpStatus.OK);
    }

}
