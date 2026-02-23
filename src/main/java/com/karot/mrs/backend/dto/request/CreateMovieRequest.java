package com.karot.mrs.backend.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieRequest {
    @NotBlank(message = "Title is required.")
    private String title;

    @NotNull(message = "Duration is required.")
    @Positive(message = "Duration must be positive.")
    private Long duration;

    private String description;

    @NotBlank(message = "Genre is required.")
    private String genre;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate releaseDate;
    private String director;
    private String actors;

    @NotBlank(message = "Language is required.")
    private String language;
}
