package com.karot.mrs.backend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.karot.mrs.backend.dto.request.CreateMovieRequest;
import com.karot.mrs.backend.dto.request.UpdateMovieRequest;
import com.karot.mrs.backend.dto.response.MovieDto;
import com.karot.mrs.backend.entity.Movie;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovieMapper {
    private final ModelMapper modelMapper;

    public Movie toEntity(CreateMovieRequest request) {
        return modelMapper.map(request, Movie.class);
    }

    public void updateEntity(UpdateMovieRequest request, Movie movie) {
        if (request.getTitle() != null)
            movie.setTitle(request.getTitle());
        if (request.getDuration() != null)
            movie.setDuration(request.getDuration());
        if (request.getDescription() != null)
            movie.setDescription(request.getDescription());
        if (request.getGenre() != null)
            movie.setGenre(request.getGenre());
        if (request.getReleaseDate() != null)
            movie.setReleaseDate(request.getReleaseDate());
        if (request.getDirector() != null)
            movie.setDirector(request.getDirector());
        if (request.getActors() != null)
            movie.setActors(request.getActors());
        if (request.getLanguage() != null)
            movie.setLanguage(request.getLanguage());
    }

    public MovieDto toDto(Movie movie) {
        MovieDto dto = modelMapper.map(movie, MovieDto.class);
        if (movie.getShowtimes() != null) {
            dto.setShowtimeCount(movie.getShowtimes().size());
        } else {
            dto.setShowtimeCount(0);
        }
        return dto;
    }
}
