package com.karot.mrs.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.karot.mrs.backend.dto.request.CreateTheaterRequest;
import com.karot.mrs.backend.dto.request.UpdateTheaterRequest;
import com.karot.mrs.backend.dto.response.TheaterDto;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.service.TheaterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/theaters")
@RequiredArgsConstructor
public class TheaterController {
    private final TheaterService theaterService;

    @GetMapping
    public ResponseEntity<List<TheaterDto>> getAllTheaters() {
        return ResponseEntity.ok(theaterService.getAllTheaters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheaterDto> getTheaterById(@PathVariable Long id) throws MrsException {
        return ResponseEntity.ok(theaterService.getTheaterById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TheaterDto>> searchTheaters(@RequestParam String name) {
        return ResponseEntity.ok(theaterService.searchTheatersByName(name));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TheaterDto> createTheater(@Valid @RequestBody CreateTheaterRequest request) {
        return new ResponseEntity<>(theaterService.createTheater(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TheaterDto> updateTheater(@PathVariable Long id,
            @Valid @RequestBody UpdateTheaterRequest request) throws MrsException {
        return ResponseEntity.ok(theaterService.updateTheater(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTheater(@PathVariable Long id) throws MrsException {
        theaterService.deleteTheater(id);
        return ResponseEntity.noContent().build();
    }
}
