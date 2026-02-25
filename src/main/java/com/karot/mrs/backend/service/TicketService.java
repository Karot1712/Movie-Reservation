package com.karot.mrs.backend.service;

import com.karot.mrs.backend.dto.TicketStatus;
import com.karot.mrs.backend.dto.response.TicketResponse;
import com.karot.mrs.backend.entity.*;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.repositories.ReservationRepository;
import com.karot.mrs.backend.repositories.TicketRepository;
import com.karot.mrs.backend.repositories.TicketSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketSeatRepository ticketSeatRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public Ticket createTicketForReservation(Reservation reservation, List<ShowtimeSeat> seats) {
        // Create a single ticket for the reservation
        Ticket ticket = new Ticket();
        ticket.setReservation(reservation);
        ticket.setStatus(TicketStatus.CONFIRMED);
        ticket.setTotalPrice(BigDecimal.ZERO);

        ticket = ticketRepository.save(ticket);

        List<String> seatCodes = new ArrayList<>();

        for (ShowtimeSeat seat : seats) {
            seat.markSold();

            TicketSeat ticketSeat = new TicketSeat();
            ticketSeat.setTicket(ticket);
            ticketSeat.setShowtimeSeat(seat);
            ticketSeat.setPriceAtPurchase(BigDecimal.ZERO);

            ticketSeatRepository.save(ticketSeat);

            seatCodes.add(seat.getSeat().getSeatCode());
        }

        String movieTitle = reservation.getShowtime().getMovie().getTitle();
        String theaterName = reservation.getShowtime().getRoom().getTheater().getName();
        String roomName = reservation.getShowtime().getRoom().getName();
        String seatsJoined = String.join(",", seatCodes);

        String qrPayload = String.format(
                "movie=%s;theater=%s;room=%s;seats=%s;total=%s",
                movieTitle,
                theaterName,
                roomName,
                seatsJoined,
                ticket.getTotalPrice() != null ? ticket.getTotalPrice() : BigDecimal.ZERO
        );
        ticket.setQrCode(qrPayload);

        return ticketRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> getTicketsByReservation(Long reservationId) throws MrsException {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new MrsException("RESERVATION_NOT_FOUND"));

        List<Ticket> tickets = ticketRepository.findByReservationId(reservationId);

        return tickets.stream()
                .map(ticket -> {
                    Showtime showtime = reservation.getShowtime();
                    String movieTitle = showtime.getMovie().getTitle();
                    String theaterName = showtime.getRoom().getTheater().getName();
                    String roomName = showtime.getRoom().getName();

                    List<TicketSeat> ticketSeats = ticketSeatRepository.findByTicket_Id(ticket.getId());
                    List<String> seatCodes = ticketSeats.stream()
                            .map(ts -> ts.getShowtimeSeat().getSeat().getSeatCode())
                            .collect(Collectors.toList());

                    Long price = ticket.getTotalPrice() != null
                            ? ticket.getTotalPrice().longValue()
                            : 0L;

                    return new TicketResponse(
                            ticket.getId().toString(),
                            movieTitle,
                            theaterName,
                            roomName,
                            seatCodes,
                            price,
                            ticket.getQrCode()
                    );
                })
                .toList();
    }
}

