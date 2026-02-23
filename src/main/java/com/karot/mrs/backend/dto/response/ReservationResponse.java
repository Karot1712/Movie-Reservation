package com.karot.mrs.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

    private Long reservationId;
    private Long showId;
    private Long userId;
    private int seats;
    private Long totalPrice;  // Total price in VND
}

