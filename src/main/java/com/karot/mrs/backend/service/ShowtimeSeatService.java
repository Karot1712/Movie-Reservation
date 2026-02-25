package com.karot.mrs.backend.service;

import com.karot.mrs.backend.dto.response.ShowtimeSeatDto;
import com.karot.mrs.backend.entity.ShowtimeSeat;
import com.karot.mrs.backend.mapper.ShowtimeSeatMapper;
import com.karot.mrs.backend.repositories.ShowtimeSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowtimeSeatService {
    private final ShowtimeSeatRepository showtimeSeatRepository;
    private final Clock clock;
    private final ShowtimeSeatMapper showtimeSeatMapper;

    @Transactional
    public List<ShowtimeSeatDto> getSeats(Long showtimeId){
        LocalDateTime now = LocalDateTime.now(clock);
        List<ShowtimeSeat> seats = showtimeSeatRepository.findAllByShowtimeId(showtimeId);

        for (ShowtimeSeat seat : seats) {
            if (seat.isLockedExpired(now)) {
                seat.releaseLock();
            }
        }

        return seats.stream()
                .map(showtimeSeatMapper::toDto)
                .toList();
    }
}

