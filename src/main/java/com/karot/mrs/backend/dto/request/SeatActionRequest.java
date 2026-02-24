package com.karot.mrs.backend.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SeatActionRequest {
    private List<Long> seatId;
}
