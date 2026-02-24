package com.karot.mrs.backend.mapper;

import com.karot.mrs.backend.dto.response.ShowtimeSeatDto;
import com.karot.mrs.backend.entity.ShowtimeSeat;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@RequiredArgsConstructor
public class ShowtimeSeatMapper {
    private final ModelMapper modelMapper;

    public ShowtimeSeatDto toDto(ShowtimeSeat showtimeSeat){
        return modelMapper.map(showtimeSeat, ShowtimeSeatDto.class);
    }
}
