package com.karot.mrs.backend.service;

import com.karot.mrs.backend.dto.SeatStatus;
import com.karot.mrs.backend.dto.response.ShowtimeSeatDto;
import com.karot.mrs.backend.entity.ShowtimeSeat;
import com.karot.mrs.backend.exception.MrsException;
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
    private final int HOLD_MINUTES = 5;

    @Transactional
    public List<ShowtimeSeatDto> lockSeats(Long showtimeId, List<Long> seatIds) throws MrsException {
        List<ShowtimeSeat> seatToLock = showtimeSeatRepository.findByShowtime_IdAndSeat_IdIn(showtimeId,seatIds);

        if(seatToLock.size() != seatIds.size()){
            throw new MrsException("SEATS_NOT_FOUND");
        }
        LocalDateTime now = LocalDateTime.now(clock);
        for(ShowtimeSeat seat: seatToLock){
            if(seat.getSeatStatus() == SeatStatus.SOLD){
                throw new MrsException("SEAT_ALREADY_SOLD");
            }
            if(!seat.isLockedExpired(now) && seat.getSeatStatus() == SeatStatus.LOCKED){
                throw new MrsException("SEAT_ALREADY_LOCKED");
            }
            seat.lock(HOLD_MINUTES);
        }
        return seatToLock.stream()
                .map(showtimeSeatMapper::toDto)
                .toList();
    }

    @Transactional
    public List<ShowtimeSeatDto> getSeats(Long showtimeId){
        LocalDateTime now = LocalDateTime.now(clock);
        List<ShowtimeSeat> seats = showtimeSeatRepository.findAllByShowtimeId(showtimeId);

        for(ShowtimeSeat seat : seats){
            if(seat.isLockedExpired(now)){
                seat.releaseLock();
            }
        }
        return seats.stream()
                .map(showtimeSeatMapper::toDto)
                .toList();
    }

    @Transactional
    public void confirmSeats(Long showtimeId, List<Long> seatIds) throws MrsException{
        List<ShowtimeSeat> seats = showtimeSeatRepository.findByShowtime_IdAndSeat_IdIn(showtimeId,seatIds);
        for(ShowtimeSeat seat: seats){
            seat.markSold();
        }
    }



}
