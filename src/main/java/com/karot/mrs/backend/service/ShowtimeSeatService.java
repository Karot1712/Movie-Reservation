package com.karot.mrs.backend.service;

import com.karot.mrs.backend.dto.SeatStatus;
import com.karot.mrs.backend.dto.request.SeatActionRequest;
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
    public List<ShowtimeSeatDto> lockSeats(Long showtimeId, SeatActionRequest seatIds) throws MrsException {
        List<ShowtimeSeat> seatToLock = showtimeSeatRepository.findAllByShowtimeIdAndSeatId(showtimeId,seatIds.getSeatId());
        if(seatToLock.size() != seatIds.getSeatId().size()){
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

    public List<ShowtimeSeatDto> getSeats(Long showtimeId){
        List<ShowtimeSeat> seats = showtimeSeatRepository.findAllByShowtimeId(showtimeId);
        return seats.stream()
                .map(showtimeSeatMapper::toDto)
                .toList();
    }

    @Transactional
    public void confirmSeats(Long showtimeId, SeatActionRequest seatIds) throws MrsException{
        List<ShowtimeSeat> seats = showtimeSeatRepository.findAllByShowtimeIdAndSeatId(showtimeId,seatIds.getSeatId());
        for(ShowtimeSeat seat: seats){
            seat.markSold();
        }
    }


    @Transactional
    public void releaseExpiredLocks(Long showtimeId){
        LocalDateTime now = LocalDateTime.now(clock);
        List<ShowtimeSeat> seats = showtimeSeatRepository.findByShowtimeIdAndSeatStatus(showtimeId,SeatStatus.LOCKED);
        for(ShowtimeSeat seat : seats){
            if(seat.isLockedExpired(now)){
                seat.releaseLock();
            }
        }
    }
}
