package com.karot.mrs.backend.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDto {
    private Long id;
    private String title;
    private Long duration;
    private String description;
    private String genre;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private String director;
    private String actors;
    private String language;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private Long version;

    // number of showtimes for this movie (avoid forcing loading of full list)
    private Integer showtimeCount;
}