package com.karot.mrs.backend.mapper;

import com.karot.mrs.backend.dto.request.UpdateShowtimeRequest;
import com.karot.mrs.backend.dto.response.ShowtimeDto;
import com.karot.mrs.backend.entity.Showtime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShowtimeMapper {
    private final ModelMapper modelMapper;

    public ShowtimeDto toDto(Showtime showtime) {
        ShowtimeDto dto = modelMapper.map(showtime, ShowtimeDto.class);
        dto.setRoomId(showtime.getRoom().getId());
        dto.setRoomName(showtime.getRoom().getName());
        dto.setMovieId(showtime.getMovie().getId());
        dto.setMovieTitle(showtime.getMovie().getTitle());
        return dto;
    }

    public void updateShowtime(UpdateShowtimeRequest request, Showtime showtime){
        if(request.getStartTime() != null)
            showtime.setStartTime(request.getStartTime());
        if(request.getEndTime() != null)
            showtime.setEndTime(request.getEndTime());
    }
}

