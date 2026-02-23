package com.karot.mrs.backend.dto.response;

import com.karot.mrs.backend.dto.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {
    private Long id;
    private String rowLabel;
    private Integer colNumber;
    private String seatCode;
    private SeatType type;
}
