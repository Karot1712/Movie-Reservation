package com.karot.mrs.backend.mapper;


import com.karot.mrs.backend.dto.response.SeatDto;
import com.karot.mrs.backend.entity.Seat;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatMapper {
    private final ModelMapper modelMapper;
    public SeatDto toDto(Seat seat) {
        return modelMapper.map(seat, SeatDto.class);
    }
}
