package com.karot.mrs.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequest {
    @NotBlank
    private String name;

    private Integer start;
    private Integer count;

    private Integer totalRows;
    private Integer totalCols;
    private Long theaterId;

}
