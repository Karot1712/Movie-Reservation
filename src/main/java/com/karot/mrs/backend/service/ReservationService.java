package com.karot.mrs.backend.service;

import com.karot.mrs.backend.dto.ReservationStatus;
import com.karot.mrs.backend.dto.SeatStatus;
import com.karot.mrs.backend.dto.request.CreateReservationRequest;
import com.karot.mrs.backend.dto.response.ReservationResponse;
import com.karot.mrs.backend.entity.Reservation;
import com.karot.mrs.backend.entity.Showtime;
import com.karot.mrs.backend.entity.ShowtimeSeat;
import com.karot.mrs.backend.entity.Ticket;
import com.karot.mrs.backend.entity.User;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.repositories.ReservationRepository;
import com.karot.mrs.backend.repositories.ShowtimeRepository;
import com.karot.mrs.backend.repositories.ShowtimeSeatRepository;
import com.karot.mrs.backend.repositories.UserRepository;
import com.karot.mrs.backend.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ShowtimeRepository showtimeRepository;
    private final ShowtimeSeatRepository showtimeSeatRepository;
    private final TicketService ticketService;
    private final UserRepository userRepository;
    private final Clock clock;

    private static final int HOLD_MINUTES = 5;

    @Transactional
    public ReservationResponse createReservation(CreateReservationRequest request) throws MrsException {
        Long showtimeId = request.getShowtimeId();
        List<Long> seatIds = request.getSeatIds();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthUser authUser)) {
            throw new MrsException("UNAUTHENTICATED");
        }

        Long userId = authUser.getId();

        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new MrsException("SHOWTIME_NOT_FOUND"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MrsException("USER_NOT_FOUND"));

        List<ShowtimeSeat> seatsToLock = showtimeSeatRepository.findByShowtime_IdAndSeat_IdIn(showtimeId, seatIds);
        if (seatsToLock.size() != seatIds.size()) {
            throw new MrsException("SEATS_NOT_FOUND");
        }

        LocalDateTime now = LocalDateTime.now(clock);

        for (ShowtimeSeat seat : seatsToLock) {
            if (seat.getSeatStatus() == SeatStatus.SOLD) {
                throw new MrsException("SEAT_ALREADY_SOLD");
            }
            if (!seat.isLockedExpired(now) && seat.getSeatStatus() == SeatStatus.LOCKED) {
                throw new MrsException("SEAT_ALREADY_LOCKED");
            }
        }

        Reservation reservation = new Reservation();
        reservation.setShowtime(showtime);
        reservation.setUser(user);
        reservation.setReservationStatus(ReservationStatus.ACTIVE);
        reservation.setExpireAt(now.plusMinutes(HOLD_MINUTES));

        reservation = reservationRepository.save(reservation);

        for (ShowtimeSeat seat : seatsToLock) {
            seat.lock(HOLD_MINUTES);
            seat.setReservation(reservation);
        }

        return ReservationResponse.builder()
                .reservationId(reservation.getId())
                .showId(showtime.getId())
                .userId(user.getId())
                .seats(seatsToLock.size())
                .totalPrice(0L)
                .build();
    }

    @Transactional
    public ReservationResponse confirmReservation(Long reservationId) throws MrsException {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new MrsException("RESERVATION_NOT_FOUND"));

        LocalDateTime now = LocalDateTime.now(clock);

        if (reservation.isExpired(now)) {
            throw new MrsException("RESERVATION_EXPIRED");
        }

        if (reservation.getReservationStatus() != ReservationStatus.ACTIVE) {
            throw new MrsException("RESERVATION_NOT_ACTIVE");
        }

        List<ShowtimeSeat> seats = showtimeSeatRepository.findByReservationId(reservationId);
        if (seats.isEmpty()) {
            throw new MrsException("NO_SEATS_FOR_RESERVATION");
        }

        Ticket ticket = ticketService.createTicketForReservation(reservation, seats);

        reservation.markConfirmed();

        long totalPrice = ticket.getTotalPrice() != null
                ? ticket.getTotalPrice().longValue()
                : 0L;

        return ReservationResponse.builder()
                .reservationId(reservation.getId())
                .showId(reservation.getShowtime().getId())
                .userId(reservation.getUser().getId())
                .seats(seats.size())
                .totalPrice(totalPrice)
                .build();
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void expireReservationsJob() {
        LocalDateTime now = LocalDateTime.now(clock);
        List<Reservation> expired = reservationRepository
                .findByReservationStatusAndExpireAtBefore(ReservationStatus.ACTIVE, now);

        for (Reservation reservation : expired) {
            reservation.markExpired();

            List<ShowtimeSeat> seats = showtimeSeatRepository.findByReservationId(reservation.getId());
            for (ShowtimeSeat seat : seats) {
                seat.releaseLock();
                seat.setReservation(null);
            }
        }
    }
}

