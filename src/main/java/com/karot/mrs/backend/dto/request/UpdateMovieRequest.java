package com.karot.mrs.backend.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMovieRequest {
    private String title;

    @Positive(message = "Duration must be positive.")
    private Long duration;

    private String description;
    private String genre;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate releaseDate;
    private String director;
    private String actors;
    private String language;
}
