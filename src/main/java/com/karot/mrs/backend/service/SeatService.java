package com.karot.mrs.backend.service;

import com.karot.mrs.backend.controller.SeatController;
import com.karot.mrs.backend.dto.SeatType;
import com.karot.mrs.backend.dto.request.CreateSeatsRequest;
import com.karot.mrs.backend.dto.request.UpdateSeatTypeRequest;
import com.karot.mrs.backend.dto.response.SeatDto;
import com.karot.mrs.backend.entity.Room;
import com.karot.mrs.backend.entity.Seat;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.mapper.SeatMapper;
import com.karot.mrs.backend.repositories.RoomRepository;
import com.karot.mrs.backend.repositories.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final RoomRepository roomRepository;
    private final SeatMapper seatMapper;

    @Transactional
    public List<SeatDto> generateSeats(Long roomId, CreateSeatsRequest request) throws MrsException {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new MrsException("ROOM_NOT_FOUND"));

        List<Seat> seats = new ArrayList<>();
        for(int i = 0; i< request.getRowLabel(); i++){
            String rowLabel = String.valueOf((char)('A' + i));
            for(int col = 1; col< request.getColNumber(); col++){
                Seat seat = Seat.builder()
                        .room(room)
                        .rowLabel(rowLabel)
                        .colNumber(col)
                        .type(SeatType.valueOf(request.getType()))
                        .build();

                seats.add(seat);
            }
        }
        return seatRepository.saveAll(seats).stream().map(seatMapper::toDto).toList();
    }

    public List<SeatDto> getAllSeats(Long roomId) throws MrsException {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new MrsException("ROOM_NOT_FOUND"));
        return seatRepository.findByRoom(room).stream().map(seatMapper::toDto).toList();
    }

    @Transactional
    public SeatDto updateSeatType(Long seatId, UpdateSeatTypeRequest request) throws MrsException {
        Seat seat = seatRepository.findById(seatId).orElseThrow(()-> new MrsException("SEAT_NOT_FOUND"));
        seat.setType(request.getType());
        return seatMapper.toDto(seat);
    }

}
