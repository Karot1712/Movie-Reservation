package com.karot.mrs.backend.service;

import com.karot.mrs.backend.dto.SeatStatus;
import com.karot.mrs.backend.dto.ShowtimeStatus;
import com.karot.mrs.backend.dto.request.CreateShowtimeRequest;
import com.karot.mrs.backend.dto.request.UpdateShowtimeRequest;
import com.karot.mrs.backend.dto.response.ShowtimeDto;
import com.karot.mrs.backend.entity.Movie;
import com.karot.mrs.backend.entity.Room;
import com.karot.mrs.backend.entity.Showtime;
import com.karot.mrs.backend.entity.ShowtimeSeat;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.mapper.ShowtimeMapper;
import com.karot.mrs.backend.repositories.MovieRepository;
import com.karot.mrs.backend.repositories.RoomRepository;
import com.karot.mrs.backend.repositories.ShowtimeRepository;
import com.karot.mrs.backend.repositories.ShowtimeSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ShowtimeMapper showtimeMapper;
    private final ShowtimeSeatRepository  showtimeSeatRepository;

    @Transactional
    public ShowtimeDto createShowtime(Long roomId, CreateShowtimeRequest request) throws MrsException {
        Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow(() -> new MrsException("MOVIE_NOT_FOUND"));
        Room room = roomRepository.findById(roomId).orElseThrow(()-> new MrsException("ROOM_NOT_FOUND"));

        validatedShowtime(request.getStartTime(),request.getEndTime());

        if(showtimeRepository.existsOverlappingShowtime(roomId, request.getStartTime(), request.getEndTime())){
            throw new MrsException("SHOWTIME_OVERLAP");
        }
        Showtime showtime = Showtime.builder()
                .movie(movie)
                .room(room)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(ShowtimeStatus.AVAILABLE)
                .build();
        Showtime savedShowtime = showtimeRepository.save(showtime);

        List<ShowtimeSeat> showtimeSeats = room.getSeats().stream()
                .map(seat -> ShowtimeSeat.builder()
                        .showtime(savedShowtime)
                        .seat(seat)
                        .seatStatus(SeatStatus.AVAILABLE)
                        .build())
                .toList();
        showtimeSeatRepository.saveAll(showtimeSeats);

        return showtimeMapper.toDto(savedShowtime);
    };

    @Transactional
    public ShowtimeDto updateShowtime(Long roomId, Long showtimeId, UpdateShowtimeRequest request) throws MrsException {
        Showtime showtime = showtimeRepository.findByIdAndRoomId(roomId,showtimeId).orElseThrow(() -> new MrsException("SHOWTIME_NOT_FOUND"));
        showtimeMapper.updateShowtime(request, showtime);
        return showtimeMapper.toDto(showtime);
    }

    public ShowtimeDto getShowtimeById(Long roomId, Long showtimeId) throws MrsException{
        Showtime showtime = showtimeRepository.findByIdAndRoomId(roomId,showtimeId).orElseThrow(() -> new MrsException("SHOWTIME_NOT_FOUND"));
        return showtimeMapper.toDto(showtime);
    }

    @Scheduled(cron =  "0 * * * * *")
    @Transactional
    public void autoClosedShowtime(){
        List<Showtime> expired = showtimeRepository.findAllExpired(LocalDateTime.now());
        expired.forEach(Showtime::close);
    }

    public void validatedShowtime(LocalDateTime start, LocalDateTime end) throws MrsException {
        if(start.isAfter(end) || start.equals(end)){
            throw new MrsException("INVALID_TIME_RANGE");
        }
    }
}
