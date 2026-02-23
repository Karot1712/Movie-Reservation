package com.karot.mrs.backend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.karot.mrs.backend.dto.request.CreateTheaterRequest;
import com.karot.mrs.backend.dto.request.UpdateTheaterRequest;
import com.karot.mrs.backend.dto.response.TheaterDto;
import com.karot.mrs.backend.entity.Theater;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TheaterMapper {
    private final ModelMapper modelMapper;

    public Theater toEntity(CreateTheaterRequest request) {
        return modelMapper.map(request, Theater.class);
    }

    public void updateEntity(UpdateTheaterRequest request, Theater theater) {
        if (request.getName() != null) theater.setName(request.getName());
        if (request.getAddress() != null) theater.setAddress(request.getAddress());
    }

    public TheaterDto toDto(Theater theater) {
        return modelMapper.map(theater, TheaterDto.class);
    }
}
