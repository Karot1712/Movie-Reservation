package com.karot.mrs.backend.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.karot.mrs.backend.dto.request.CreateMovieRequest;
import com.karot.mrs.backend.dto.request.UpdateMovieRequest;
import com.karot.mrs.backend.dto.response.MovieDto;
import com.karot.mrs.backend.entity.Movie;
import com.karot.mrs.backend.exception.MrsException;
import com.karot.mrs.backend.mapper.MovieMapper;
import com.karot.mrs.backend.repositories.MovieRepository;

import lombok.RequiredArgsConstructor;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream().map(movieMapper::toDto).toList();
    }

    public MovieDto getMovieById(Long id) throws MrsException {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MrsException("MOVIE_NOT_FOUND"));
        return movieMapper.toDto(movie);
    }

    public List<MovieDto> getMoviesByGenre(String genre) {
        return movieRepository.findByGenreIgnoreCase(genre).stream().map(movieMapper::toDto).toList();
    }

    public List<MovieDto> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title).stream().map(movieMapper::toDto).toList();
    }

    public MovieDto createMovie(CreateMovieRequest request) {
        Movie movie = movieMapper.toEntity(request);
        Movie saved = movieRepository.save(movie);
        return movieMapper.toDto(saved);
    }

    public MovieDto updateMovie(Long id, UpdateMovieRequest request) throws MrsException {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MrsException("MOVIE_NOT_FOUND"));
        movieMapper.updateEntity(request, movie);
        Movie updated = movieRepository.save(movie);
        return movieMapper.toDto(updated);
    }

    public void deleteMovie(Long id) throws MrsException {
        if (!movieRepository.existsById(id)) {
            throw new MrsException("MOVIE_NOT_FOUND");
        }
        movieRepository.deleteById(id);
    }
}
