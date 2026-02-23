package com.karot.mrs.backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveSeatsRequest {

    @NotNull(message = "User id is required.")
    private Long userId;

    @Min(value = 1, message = "Seats must be at least 1.")
    private int seats;
}

