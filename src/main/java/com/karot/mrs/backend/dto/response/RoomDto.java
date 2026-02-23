package com.karot.mrs.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDto {
    private Long id;
    private String name;
    private Integer totalRows;
    private Integer totalCols;
    private Long theaterId;
    private String theaterName;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
}
