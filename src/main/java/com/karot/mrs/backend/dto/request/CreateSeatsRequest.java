package com.karot.mrs.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreateSeatsRequest {
    private Integer rowLabel;
    private Integer colNumber;
    private String type;
}
