package com.karot.mrs.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.karot.mrs.backend.dto.request.CreateTheaterRequest;
import com.karot.mrs.backend.dto.request.UpdateTheaterRequest;
import com.karot.mrs.backend.dto.response.TheaterDto;
import com.karot.mrs.backend.entity.Theater;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.mapper.TheaterMapper;
import com.karot.mrs.backend.repositories.TheaterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TheaterService {
    private final TheaterRepository theaterRepository;
    private final TheaterMapper theaterMapper;

    public List<TheaterDto> getAllTheaters() {
        return theaterRepository.findAll().stream().map(theaterMapper::toDto).toList();
    }

    public TheaterDto getTheaterById(Long id) throws MrsException {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new MrsException("THEATER_NOT_FOUND"));
        return theaterMapper.toDto(theater);
    }

    public List<TheaterDto> searchTheatersByName(String name) {
        return theaterRepository.findByNameContainingIgnoreCase(name).stream()
                .map(theaterMapper::toDto).toList();
    }

    public TheaterDto createTheater(CreateTheaterRequest request) {
        Theater theater = theaterMapper.toEntity(request);
        Theater saved = theaterRepository.save(theater);
        return theaterMapper.toDto(saved);
    }

    public TheaterDto updateTheater(Long id, UpdateTheaterRequest request) throws MrsException {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new MrsException("THEATER_NOT_FOUND"));
        theaterMapper.updateEntity(request, theater);
        Theater updated = theaterRepository.save(theater);
        return theaterMapper.toDto(updated);
    }

    public void deleteTheater(Long id) throws MrsException {
        if (!theaterRepository.existsById(id)) {
            throw new MrsException("THEATER_NOT_FOUND");
        }
        theaterRepository.deleteById(id);
    }
}
