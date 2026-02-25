package com.karot.mrs.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {

    private String ticketId;
    private String movieTitle;
    private String theaterName;
    private String roomName;
    private List<String> seatCodes;
    private Long totalPrice; // in VND
    private String qrCode;
}

