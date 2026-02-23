package com.karot.mrs.backend.mapper;

import com.karot.mrs.backend.dto.request.CreateRoomRequest;
import com.karot.mrs.backend.dto.request.UpdateRoomRequest;
import com.karot.mrs.backend.dto.response.RoomDto;
import com.karot.mrs.backend.entity.Room;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomMapper {
    private final ModelMapper modelMapper;

    public void updateEntity(UpdateRoomRequest request, Room room){
        if(request.getName() != null){
            room.setName(request.getName());
        }if(request.getTotalCols() != null){
            room.setName(request.getName());
        }if(request.getTotalRows() != null){
            room.setTotalRows(request.getTotalRows());
        }

    }

    public RoomDto toDto(Room room){
        return modelMapper.map(room, RoomDto.class);
    }
}
