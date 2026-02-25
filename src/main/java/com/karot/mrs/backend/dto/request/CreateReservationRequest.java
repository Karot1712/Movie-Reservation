package com.karot.mrs.backend.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateReservationRequest {
    private Long showtimeId;
    private List<Long> seatIds;
}

