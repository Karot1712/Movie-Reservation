package com.karot.mrs.backend.dto.request;

import com.karot.mrs.backend.dto.SeatType;
import lombok.Data;

@Data
public class UpdateSeatTypeRequest {
    private SeatType type;
}
