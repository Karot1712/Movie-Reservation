package com.karot.mrs.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.karot.mrs.backend.dto.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeSeatDto {
    private Long id;

    private Long showtimeId;
    private Long seatId;
    private String seatCode;

    private SeatStatus seatStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lockedUntil;
}

