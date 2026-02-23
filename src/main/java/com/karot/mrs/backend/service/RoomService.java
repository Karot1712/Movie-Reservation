package com.karot.mrs.backend.service;

import com.karot.mrs.backend.dto.request.CreateRoomRequest;
import com.karot.mrs.backend.dto.request.UpdateRoomRequest;
import com.karot.mrs.backend.dto.response.RoomDto;
import com.karot.mrs.backend.entity.Room;
import com.karot.mrs.backend.entity.Theater;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.mapper.RoomMapper;
import com.karot.mrs.backend.repositories.RoomRepository;
import com.karot.mrs.backend.repositories.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final TheaterRepository theaterRepository;
    private final RoomMapper roomMapper;

    public List<RoomDto> getAllRooms(){
        return roomRepository.findAll().stream().map(roomMapper::toDto).toList();
    }

    @Transactional
    public List<RoomDto> createRoom(Long theaterId,CreateRoomRequest request) throws MrsException {
        Theater theater = theaterRepository.findById(theaterId).orElseThrow(()-> new MrsException("THEATER_NOT_FOUND"));
        List<String> generateNames = new ArrayList<>();
        for(int i = 0; i< request.getCount();i++){
            generateNames.add(request.getName() + (request.getStart() + i));
        }

        List<String> existNames = roomRepository.findExistingName(theaterId, generateNames);
        if (!existNames.isEmpty()){
            throw new MrsException("ROOM_NAME_ALREADY_EXIST");
        }
        List<Room> rooms = generateNames.stream()
                .map(name -> Room.builder()
                        .name(name)
                        .totalCols(request.getTotalCols())
                        .totalRows(request.getTotalRows())
                        .theater(theater)
                        .build())
                .toList();
        List<Room> saved = roomRepository.saveAll(rooms);
        return saved.stream().map(roomMapper::toDto).toList();
    }

    @Transactional
    public RoomDto updateRoom(Long theaterId, Long roomId, UpdateRoomRequest request) throws MrsException {
        Room room = roomRepository.findByIdAndTheaterId(roomId, theaterId).orElseThrow(()-> new MrsException("ROOM_NOT_FOUND"));
        roomMapper.updateEntity(request, room);
        return roomMapper.toDto(room); // with @Transactional, the changes will be automatically saved when the method ends
    }

    public RoomDto getRoomById(Long theaterId, Long roomId) throws MrsException {
        Room room = roomRepository.findByIdAndTheaterId(roomId, theaterId).orElseThrow(()-> new MrsException("ROOM_NOT_FOUND"));
        return roomMapper.toDto(room);
    }
}
